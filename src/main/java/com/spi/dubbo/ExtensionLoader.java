/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.spi.dubbo;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.regex.Pattern;

/**
 * 	加载Dubbo扩展
 * 1、自动插入依赖项扩展
 * 2、包装中的自动包装扩展
 * 3、默认扩展名是自适应实例
 *
 */
public class ExtensionLoader<T> {

	private static final String SERVICES_DIRECTORY = "META-INF/services/";

	private static final String DUBBO_DIRECTORY = "META-INF/dubbo/";

	private static final String DUBBO_INTERNAL_DIRECTORY = DUBBO_DIRECTORY + "internal/";

	private static final Pattern NAME_SEPARATOR = Pattern.compile("\\s*[,]+\\s*");

	private static final ConcurrentMap<Class<?>, ExtensionLoader<?>> EXTENSION_LOADERS = new ConcurrentHashMap<>();

	private static final ConcurrentMap<Class<?>, Object> EXTENSION_INSTANCES = new ConcurrentHashMap<>();

	// ==============================

	private final Class<?> type;

	private final ExtensionFactory objectFactory;

	private final ConcurrentMap<Class<?>, String> cachedNames = new ConcurrentHashMap<>();

	private final Map<String, Object> cachedActivates = new ConcurrentHashMap<>();
	private volatile Class<?> cachedAdaptiveClass = null;

	private Set<Class<?>> cachedWrapperClasses;

	private ExtensionLoader(Class<?> type) {
		this.type = type;
		objectFactory = (type == ExtensionFactory.class ? null
				: ExtensionLoader.getExtensionLoader(ExtensionFactory.class).getAdaptiveExtension());
	}

	@SuppressWarnings("unchecked")
	public static <T> ExtensionLoader<T> getExtensionLoader(Class<T> type) {
		ExtensionLoader<T> loader = (ExtensionLoader<T>) EXTENSION_LOADERS.get(type);
		if (loader == null) {
			EXTENSION_LOADERS.putIfAbsent(type, new ExtensionLoader<T>(type));
			loader = (ExtensionLoader<T>) EXTENSION_LOADERS.get(type);
		}
		return loader;
	}

	private static ClassLoader findClassLoader() {
		return ClassUtils.getClassLoader(ExtensionLoader.class);
	}

	public Set<String> getSupportedExtensions() {
		Map<String, Class<?>> clazzes = getExtensionClasses();
		return Collections.unmodifiableSet(new TreeSet<>(clazzes.keySet()));
	}

	/**
	 * 1、查找具有给定名称的扩展名�??
	 * 2、如果未找到指定的名称，抛出异常illegalstateexception
	 */
	@SuppressWarnings("unchecked")
	public T getExtension(String name) {
		Object instance = createExtension(name);
		return (T) instance;
	}

	/**
	 * 1、�?�过getExtensionClasses获取�?有扩展类�?
	 * 2、�?�过反射创建扩展对象�? 
	 * 3、向扩展对象中注入依赖�?? 
	 * 4、将扩展对象包裹在相应的 Wrapper对象中�??
	 * 
	 * @param name
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private T createExtension(String name) {
		// 从配置文件中加载�?有扩展类，得�? “配置项名称�? -> “配置类�? 的映射关�?
		Class<?> clazz = getExtensionClasses().get(name);
		if (clazz == null) {
			throw new IllegalStateException("配置文件加载扩展类失�?");
		}
		try {
			T instance = (T) EXTENSION_INSTANCES.get(clazz);
			if (instance == null) {
				// 通过反射创建实例
				EXTENSION_INSTANCES.putIfAbsent(clazz, clazz.newInstance());
				instance = (T) EXTENSION_INSTANCES.get(clazz);
			}
			// 向实例中注入依赖
			injectExtension(instance);
			Set<Class<?>> wrapperClasses = cachedWrapperClasses;
			if (ExtensionLoaderUtil.isNotEmpty(wrapperClasses)) {
				for (Class<?> wrapperClass : wrapperClasses) {
					/**
					 * 1、将当前 instance 作为参数传给 Wrapper 的构造方法，并�?�过反射创建 Wrapper 实例�? 2、向 Wrapper
					 * 实例中注入依赖，�?后将 Wrapper 实例再次赋�?�给 instance 变量�?
					 */
					instance = injectExtension((T) wrapperClass.getConstructor(type).newInstance(instance));
				}
			}
			return instance;
		} catch (Throwable t) {
			throw new IllegalStateException("Extension instance (name: " + name + ", class: " + type
					+ ") couldn't be instantiated: " + t.getMessage(), t);
		}
	}

	private T injectExtension(T instance) {
		try {
			if (objectFactory != null) {
				for (Method method : instance.getClass().getMethods()) {
					// 判断是不是set
					if (isSetter(method)) {
						// �?查disableinject查看是否�?要为此属性自动注�?
						if (method.getAnnotation(DisableInject.class) != null) {
							continue;
						}
						Class<?> pt = method.getParameterTypes()[0];
						if (ExtensionLoaderUtil.isPrimitives(pt)) {
							continue;
						}
						try {
							// 获取setter的属性名�?
							String property = getSetterProperty(method);
							Object object = objectFactory.getExtension(pt, property);
							if (object != null) {
								method.invoke(instance, object);
							}
						} catch (Exception e) {
						}
					}
				}
			}
		} catch (Exception e) {
		}
		return instance;
	}

	private String getSetterProperty(Method method) {
		return method.getName().length() > 3
				? method.getName().substring(3, 4).toLowerCase() + method.getName().substring(4)
				: "";
	}

	/**
	 * 1、public
	 * 2、set
	 * 3、一个参数
	 */
	private boolean isSetter(Method method) {
		return method.getName().startsWith("set") && method.getParameterTypes().length == 1
				&& Modifier.isPublic(method.getModifiers());
	}

	@SuppressWarnings("unchecked")
	public T getAdaptiveExtension() {
		Object instance = createAdaptiveExtension();
		return (T) instance;
	}

	@SuppressWarnings("unchecked")
	private T createAdaptiveExtension() {
		try {
			// 获取自�?�应扩展类，通过反射实例化�??
			return injectExtension((T) getAdaptiveExtensionClass().newInstance());
		} catch (Exception e) {
			throw new IllegalStateException("Can't create adaptive extension " + type + ", cause: " + e.getMessage(),
					e);
		}
	}

	private Class<?> getAdaptiveExtensionClass() {
		/**
		 * 1、�?�过SPI获取�?有的扩展类，获取某个接口的所有实现类�?
		 * 2、例如可以获取Protocol接口的DubboProtocol，HttpProtocol，InjvmProtocol等实现类�?
		 * 3、实现过程中如果某个实现类被Adaptive注解修饰，该类被赋�?�给cachedAdaptiveClass，直接返回cachedAdaptiveClass
		 * 4、如果所有的实现类均未被 Adaptive注解修饰，那么执行第三步逻辑，创建自适应拓展类�??
		 */
		getExtensionClasses();
		// �?查缓存，若缓存不为空，直接返回缓�?
		if (cachedAdaptiveClass != null) {
			return cachedAdaptiveClass;
		}
		/**
		 * 	创建自使用扩展类
		 */
		return null;
	}
	
	/**
	 * 	获取�?有扩展类
	 * @return
	 */
	private Map<String, Class<?>> getExtensionClasses() {
		// 获取 SPI 注解
		final SPI defaultAnnotation = type.getAnnotation(SPI.class);
		if (defaultAnnotation != null) {
			String value = defaultAnnotation.value();
			if ((value = value.trim()).length() > 0) {
				// �? SPI 注解内容进行切分�?
				String[] names = NAME_SEPARATOR.split(value);
				// �?�? SPI 是否合法，不合法抛出异常�?
				if (names.length > 1) {
					throw new IllegalStateException("More than 1 default extension name on extension " + type.getName()
							+ ": " + Arrays.toString(names));
				}
			}
		}

		Map<String, Class<?>> extensionClasses = new HashMap<>();
		// 加载指定文件下的配置文件
		loadDirectory(extensionClasses, DUBBO_INTERNAL_DIRECTORY, type.getName());
		loadDirectory(extensionClasses, DUBBO_INTERNAL_DIRECTORY, type.getName().replace("org.apache", "com.alibaba"));
		loadDirectory(extensionClasses, DUBBO_DIRECTORY, type.getName());
		loadDirectory(extensionClasses, DUBBO_DIRECTORY, type.getName().replace("org.apache", "com.alibaba"));
		loadDirectory(extensionClasses, SERVICES_DIRECTORY, type.getName());
		loadDirectory(extensionClasses, SERVICES_DIRECTORY, type.getName().replace("org.apache", "com.alibaba"));
		return extensionClasses;
	}

	private void loadDirectory(Map<String, Class<?>> extensionClasses, String dir, String type) {
		// fileName = 文件夹路�? + type 全限定名
		String fileName = dir + type;
		try {
			Enumeration<java.net.URL> urls;
			ClassLoader classLoader = findClassLoader();
			// 根据文件名加载所有同名文�?
			if (classLoader != null) {
				urls = classLoader.getResources(fileName);
			} else {
				urls = ClassLoader.getSystemResources(fileName);
			}
			if (urls != null) {
				while (urls.hasMoreElements()) {
					java.net.URL resourceURL = urls.nextElement();
					// 加载资源
					loadResource(extensionClasses, classLoader, resourceURL);
				}
			}
		} catch (Throwable t) {
		}
	}

	@SuppressWarnings("resource")
	private void loadResource(Map<String, Class<?>> extensionClasses, ClassLoader classLoader,
			java.net.URL resourceURL) {
		try {
			try (BufferedReader reader = new BufferedReader(
					new InputStreamReader(resourceURL.openStream(), StandardCharsets.UTF_8))) {
				String line;
				// 按行读取配置内容
				while ((line = reader.readLine()) != null) {
					// 定位 # 字符
					final int ci = line.indexOf('#');
					if (ci >= 0) {
						// 获取 # 之前的字符串，后面的内容作为注释忽略
						line = line.substring(0, ci);
					}
					line = line.trim();
					if (line.length() > 0) {
						try {
							String name = null;
							int i = line.indexOf('=');
							if (i > 0) {
								// 以等�? = 为界，截取键和�??
								name = line.substring(0, i).trim();
								line = line.substring(i + 1).trim();
							}
							if (line.length() > 0) {
								// 加载类，通过 loadClass 方法对类进行缓存
								loadClass(extensionClasses, resourceURL, Class.forName(line, true, classLoader), name);
							}
						} catch (Throwable t) {
							throw new IllegalStateException("Failed to load extension class (interface: " + type + ", class line: " + line
											+ ") in " + resourceURL + ", cause: " + t.getMessage(), t);
						}
					}
				}
			}
		} catch (Throwable t) {
			
		}
	}

	private void loadClass(Map<String, Class<?>> extensionClasses, java.net.URL resourceURL, Class<?> clazz,
			String name) throws NoSuchMethodException {
		if (!type.isAssignableFrom(clazz)) {
			throw new IllegalStateException(
					"Error occurred when loading extension class (interface: " + type + ", class line: "
							+ clazz.getName() + "), class " + clazz.getName() + " is not subtype of interface.");
		}
		// �?测目标类是否�? Adaptive 注解
		if (clazz.isAnnotationPresent(Adaptive.class)) {
			if (cachedAdaptiveClass == null) {
				// 设置缓存
				cachedAdaptiveClass = clazz;
			} else if (!cachedAdaptiveClass.equals(clazz)) {
				throw new IllegalStateException("More than 1 adaptive class found: "
						+ cachedAdaptiveClass.getClass().getName() + ", " + clazz.getClass().getName());
			}
			// �?测目标类是否�? Wrapper 类型
		} else if (isWrapperClass(clazz)) {
			if (cachedWrapperClasses == null) {
				cachedWrapperClasses = new ConcurrentHashSet<>();
			}
			// 设置缓存
			cachedWrapperClasses.add(clazz);
			// 表明目标类是�?个普通扩展类
		} else {
			clazz.getConstructor();
			if (ExtensionLoaderUtil.isEmpty(name)) {
				// 获取名称，尝试从Extention注解中获取，或�?�使用小写的类名
				name = findAnnotationName(clazz);
				if (name.length() == 0) {
					throw new IllegalStateException("No such extension name for the class " + clazz.getName()
							+ " in the config " + resourceURL);
				}
			}
			// 切分名称
			String[] names = NAME_SEPARATOR.split(name);
			if (ExtensionLoaderUtil.isNotEmpty(names)) {
				// 如果类上有Activate注解，使用names数组的第�?个元素作为键，存�? name到Activate注解对象的映射关�?
				Activate activate = clazz.getAnnotation(Activate.class);
				if (activate != null) {
					cachedActivates.put(names[0], activate);
				} else {
					// support com.alibaba.dubbo.common.extension.Activate
					Activate oldActivate = clazz.getAnnotation(Activate.class);
					if (oldActivate != null) {
						cachedActivates.put(names[0], oldActivate);
					}
				}
				for (String n : names) {
					// 存储 class -> name 的映射关�?
					if (!cachedNames.containsKey(clazz)) {
						cachedNames.put(clazz, n);
					}
					// 存储 name -> class 的映射关�?
					Class<?> c = extensionClasses.get(name);
					if (c == null) {
						extensionClasses.put(name, clazz);
					} else if (c != clazz) {
						throw new IllegalStateException("Duplicate extension " + type.getName() + " name " + name
								+ " on " + c.getName() + " and " + clazz.getName());
					}
				}
			}
		}
	}
	
	/**
	 * 	判断clazz是否是包装类
	 */
	private boolean isWrapperClass(Class<?> clazz) {
		try {
			clazz.getConstructor(type);
			return true;
		} catch (NoSuchMethodException e) {
			return false;
		}
	}
	
	@SuppressWarnings("deprecation")
	private String findAnnotationName(Class<?> clazz) {
		Extension extension = clazz.getAnnotation(Extension.class);
		if (extension == null) {
			String name = clazz.getSimpleName();
			if (name.endsWith(type.getSimpleName())) {
				name = name.substring(0, name.length() - type.getSimpleName().length());
			}
			return name.toLowerCase();
		}
		return extension.value();
	}

	public String toString() {
		return this.getClass().getName() + "[" + type.getName() + "]";
	}

}

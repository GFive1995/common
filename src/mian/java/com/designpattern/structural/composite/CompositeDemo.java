package com.designpattern.structural.composite;


public class CompositeDemo {

	public static void main(String[] args) {
		Employee CEO = new Employee("张三", "CEO", 30000);
		
		Employee headSales = new Employee("李四", "Head Sales", 20000);
		
		Employee headMarketing = new Employee("王五", "Head Marketing", 20000);
		
		Employee clerk1 = new Employee("赵小六", "Marketing", 10000);
		Employee clerk2 = new Employee("赵大六", "Marketing", 10000);
		
		Employee salesExecutive1 = new Employee("孙小七", "Sales", 10000);
		Employee salesExecutive2 = new Employee("孙大七", "Sales", 10000);
		
		CEO.add(headSales);
		CEO.add(headMarketing);
		
		headSales.add(salesExecutive1);
		headSales.add(salesExecutive2);
	
		headMarketing.add(clerk1);
		headMarketing.add(clerk2);
	
		System.out.println(CEO);
		
		for (Employee headEmployee : CEO.getSubordinates()) {
			System.out.println(headEmployee);
			for (Employee employee : headEmployee.getSubordinates()) {
				System.out.println(employee);
			}
		}
	}
	
}

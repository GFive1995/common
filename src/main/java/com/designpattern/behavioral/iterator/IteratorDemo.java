package com.designpattern.behavioral.iterator;


public class IteratorDemo {

	public static void main(String[] args) {
		NameRepositiry nameRepositiry = new NameRepositiry();
		
		for (Iterator iterator=nameRepositiry.getIterator(); iterator.hasNext();) {
			System.out.println("Name : " + iterator.next());
		}
	}

}

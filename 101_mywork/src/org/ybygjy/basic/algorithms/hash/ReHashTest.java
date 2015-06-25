package org.ybygjy.basic.algorithms.hash;

/**
 * 再哈希
 * @author WangYanCheng
 * @version 2015年6月12日
 */
public class ReHashTest {
	private DataItem[] dataItem;
	private int arraySize;
	private DataItem noneItem;
	public ReHashTest(int size) {
		this.arraySize = size;
		this.dataItem = new DataItem[size];
		this.noneItem = new DataItem(-1);
	}
	public int hash(int key) {
		return key % this.arraySize;
	}
	public int rehash(int key) {
		return 5 - key % 5;
	}
	public void displayTable() {
		for(int i = 0; i < this.arraySize; i++) {
			if (this.dataItem[i].getKey() != this.noneItem.getKey()) {
				System.out.print(this.dataItem[i].getKey() + " ");
				if (i % 10 == 0) {
					System.out.println();
				}
			}
		}
		System.out.println("**");
	}
	public void insert(int key, DataItem dataItem) {
		int hashValue = this.hash(key);
		int stepSize = this.rehash(key);
		while (this.dataItem[hashValue] != null 
				&& this.dataItem[hashValue].getKey() != this.noneItem.getKey()) {
			hashValue += stepSize;
			hashValue = hashValue % this.arraySize;
		}
		this.dataItem[hashValue] = dataItem;
	}
	public DataItem delete(int key) {
		int hashValue = this.hash(key);
		int stepSize = this.rehash(key);
		while (this.dataItem[hashValue] != null) {
			if(this.dataItem[hashValue].getKey() == key) {
				DataItem oldValue = this.dataItem[hashValue];
				this.dataItem[hashValue] = this.noneItem;
				return oldValue;
			}
			hashValue = hashValue + stepSize;
			hashValue = hashValue % this.arraySize;
		}
		return null;
	}
	public void find(int key) {
		
	}
	private class DataItem {
		private int key;
		public DataItem(int key) {
			this.key = key;
		}
		public int getKey() {
			return this.key;
		}
	}
}
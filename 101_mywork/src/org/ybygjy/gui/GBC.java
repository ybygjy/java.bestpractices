package org.ybygjy.gui;

import java.awt.GridBagConstraints;
import java.awt.Insets;

/**
 * 扩展GridBagConstraints，对外提供简洁的GridBagConstraints工具类
 * @author WangYanCheng
 * @version 2015年7月12日
 * @see GridBagConstraints
 */
public class GBC extends GridBagConstraints {
	/** serial number*/
	private static final long serialVersionUID = 3275284211480186045L;
	/**
	 * @param gridx
	 * @param gridy
	 */
	public GBC(int gridx, int gridy) {
		this.gridx = gridx;
		this.gridy = gridy;
	}
	/**
	 * @param gridx
	 * @param gridy
	 * @param gridwidth
	 * @param gridheight
	 */
	public GBC(int gridx, int gridy, int gridwidth, int gridheight) {
		this(gridx, gridy);
		this.gridwidth = gridwidth;
		this.gridheight = gridheight;
	}
	public GBC setAnchor(int anchor) {
		this.anchor = anchor;
		return this;
	}
	public GBC setFill(int fill) {
		this.fill = fill;
		return this;
	}
	public GBC setWeight(double weightx, double weighty) {
		this.weightx = weightx;
		this.weighty = weighty;
		return this;
	}
	/**
	 * Sets the insets of this cell
	 * @param distance
	 * @return
	 */
	public GBC setInsets(int distance) {
		this.insets = new Insets(distance, distance, distance, distance);
		return this;
	}
	/**
	 * Sets the insets of this cell
	 * @param top
	 * @param left
	 * @param bottom
	 * @param right
	 * @return
	 */
	public GBC setInsets(int top, int left, int bottom, int right) {
		this.insets = new Insets(top, left, bottom, right);
		return this;
	}
	/**
	 * Sets the internal padding
	 * @param ipadx
	 * @param ipady
	 * @return
	 */
	public GBC setIpad(int ipadx, int ipady) {
		this.ipadx = ipadx;
		this.ipady = ipady;
		return this;
	}
}

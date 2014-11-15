package vo;

import dataenum.Date;

/**
 * 经营情况表
 * 统计显示一段时间内的经营收支状况和利润。
 * 经营收入显示为折让后，并显示出折让了多少。
 * 显示信息：	
 * 1. 收入类：销售收入、商品类收入（商品报溢收入 成本调价收入 进货退货差价 代金券与实际收款差额收入）。
 * 	  收入类显示折让后总收入，并显示折让了多少。
 * 2. 支出类：销售成本、商品类支出（商品报损 商品赠出）。支出类显示总支出。
 * 3. 利润：折让后总收入-总支出。
 * @author Zing
 * @version 2014年11月2日下午3:50:31
 * TODO 这个要怎么弄……
 */
public class BussinessStateVO {
	/** 起始时间 */
	public Date begin;
	/** 结束时间 */
	public Date end;
	/** 销售收入*/
	public double saleIncome;
	/** 商品类收入（折让后的总收入）*/
	public double commodityIncome;
	/** 商品报溢收入*/
	public double overflowIncome;
	/** 成本调价收入*/
	public double costIncome;
	/** 进货退货差价*/
	public double purAndBackIncome;
	/** 代金券与实际收款差额收入*/
	public double voucherIncome;
	/** 折让*/
	public double allowance;
	/** 销售成本*/
	public double saleCost;
	/** 商品类支出（也就是总支出）*/
	public double commodityExpen;
	/** 商品报损*/
	public double lossExpen;
	/** 赠送支出*/
	public double giftExpen;
	/** 利润 */
	public double profit;
	
	public BussinessStateVO(Date begin, Date end){
		this.begin = begin;
		this.end = end;
	}
}
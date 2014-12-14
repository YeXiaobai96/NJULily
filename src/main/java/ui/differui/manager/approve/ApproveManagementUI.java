package ui.differui.manager.approve;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;





import java.rmi.RemoteException;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;

import message.ResultMessage;
import dataenum.BillType;
import blservice.approvalblservice.ApprovalBLService;
import businesslogic.approvalbl.ApprovalController;
import businesslogic.approvalbl.ApprovalShow;
import ui.commonui.exitfinish.ExitFinishFrame;
import ui.commonui.myui.MyComboBox;
import ui.commonui.warning.WarningFrame;
import vo.AccountBillVO;
import vo.CashBillVO;
import vo.InventoryBillVO;
import vo.PurchaseVO;
import vo.ValueObject;
import vo.sale.SalesVO;


public class ApproveManagementUI extends JLabel implements ActionListener{
	
	private static final long serialVersionUID = 1L;
	
	JTable table;
	DefaultTableModel model;
	
	Color foreColor = new Color(158, 213, 220);
	Color backColor = new Color(46, 52, 101);
	
	JButton bt_approval, bt_approvalAll, bt_search, bt_modify, bt_check, bt_out;
	public static JButton passBill;
	
	MyComboBox cbb_isApproval, cbb_sort;
	JTextArea ta;
	
	private static int rowNum = 0;
	
	static ArrayList<ValueObject> listPool;
	static ArrayList<BillType> typePool;

	private static ArrayList<ValueObject> passList;
	private static ArrayList<BillType> passType;
	
	public ApproveManagementUI(){
		
		listPool = new ArrayList<ValueObject>();
		typePool = new ArrayList<BillType>();
		
		passList = new ArrayList<ValueObject>();
		passType = new ArrayList<BillType>();
		
		this.setLayout(null);
		this.setBounds(0, 0, 1280, 720);
		this.setOpaque(false);
			
		JLabel infoBar = new JLabel("审批单据");
		infoBar.setFont(new Font("华文细黑", Font.BOLD, 18));
		infoBar.setBounds(80, 14, 1100, 20);
		infoBar.setForeground(Color.GRAY);
		infoBar.setOpaque(false);
		infoBar.setVisible(true);
		this.add(infoBar);
		
		String[] comboBoxStr = {"全部种类", "进货类单据", "销售类单据", "财务类单据", "库存类单据", "现金类单据"};
		cbb_sort = new MyComboBox(85 + 135, 56, 130, 25, comboBoxStr);
		this.add(cbb_sort);
		
		String[] comboBoxStr2 = {"全部状态", "待审批", "通过审批", "未通过审批"};
		cbb_isApproval = new MyComboBox(85, 56, 130, 25, comboBoxStr2);
		this.add(cbb_isApproval);
		
		initTable();
		initButtons();
		
		ta = new JTextArea();
		ta.setEditable(false);
		ta.setBackground(backColor);
		ta.setForeground(foreColor);
		
		JScrollPane jsp2 = new JScrollPane(ta);
		jsp2.setBounds(520, 95, 650, 465);
		this.add(jsp2);
		
		passBill = new JButton();
		passBill.addActionListener(this);
		this.add(passBill);
	}
	
	private void initButtons(){
		bt_search = new JButton("搜索");
		bt_search.setBounds(355, 56, 107, 25);
		bt_search.addActionListener(this);
		bt_search.setBackground(Color.WHITE);
		bt_search.setForeground(backColor);
		bt_search.setVisible(true);
		this.add(bt_search);
		
		bt_check = new JButton("查看所选单据内容");
		bt_check.setBounds(285, 572, 180, 25);
		bt_check.addActionListener(this);
		bt_check.setBackground(Color.WHITE);
		bt_check.setForeground(backColor);
		bt_check.setVisible(true);
		this.add(bt_check);
		
		bt_approvalAll = new JButton("通过所有所选单据");
		bt_approvalAll.setBounds(85, 572, 180, 25);
		bt_approvalAll.addActionListener(this);
		bt_approvalAll.setBackground(Color.WHITE);
		bt_approvalAll.setForeground(backColor);
		bt_approvalAll.setVisible(true);
		this.add(bt_approvalAll);
		
		bt_approval = new JButton("通过此单据");
		bt_approval.setBounds(1170 - 180, 572, 180, 25);
		bt_approval.addActionListener(this);
		bt_approval.setBackground(Color.WHITE);
		bt_approval.setForeground(backColor);
		bt_approval.setVisible(true);
		this.add(bt_approval);
		
		bt_modify = new JButton("修改单据内容");
		bt_modify.setBounds(520, 572, 180, 25);
		bt_modify.addActionListener(this);
		bt_modify.setBackground(Color.WHITE);
		bt_modify.setForeground(backColor);
		bt_modify.setVisible(true);
		this.add(bt_modify);
		
		bt_out = new JButton("导出单据至桌面");
		bt_out.setBounds(755, 572, 180, 25);
		bt_out.addActionListener(this);
		bt_out.setBackground(Color.WHITE);
		bt_out.setForeground(backColor);
		bt_out.setVisible(true);
		this.add(bt_out);
	}
	
	public void actionPerformed(ActionEvent events) {
		
		if(events.getSource() == bt_approvalAll){
						
			int count = 0;
			boolean flag = true;
			
			for(int i = 0; i < rowNum; i++){
				if(table.getValueAt(i, 0).equals(Boolean.TRUE)){
					count++;
				}
				if(table.getValueAt(i, 0).equals(Boolean.TRUE) && 
						!table.getValueAt(i, 3).equals("未审批")){
					flag = false;
				}
			}
			
			if(flag == true){
				if(count == 0){
					WarningFrame wf = new WarningFrame("请先选择需要通过审批的单据!");
					wf.setVisible(true);
				}else{
					ExitFinishFrame wff = new ExitFinishFrame("passBill");
					wff.setVisible(true);
				}
			}else{
				WarningFrame wf = new WarningFrame("只可对未审批的单据进行审批!");
				wf.setVisible(true);
			}
		}
		
		if(events.getSource() == passBill){
			ApprovalBLService controller = new ApprovalController();
					
			for(int i = 0; i < rowNum; i++){
				if(table.getValueAt(i, 0).equals(Boolean.TRUE)){
					passList.add(listPool.get(i));
					passType.add(typePool.get(i));
				}
			}
			
			ResultMessage rm = controller.passBill(passList, passType);
			
			if(rm.equals(ResultMessage.FAILURE)){
				WarningFrame wf = new WarningFrame("批量审批失败！");
				wf.setVisible(true);
			}else{
				
				bt_search.doClick();
					
				WarningFrame wf = new WarningFrame("批量审批成功！");
				wf.setVisible(true);
				
			}
		}
		
		if(events.getSource() == bt_search){

			//清空VO储存池
			listPool.clear();
			typePool.clear();
			
			DefaultTableModel tableModel = (DefaultTableModel) table.getModel();
			
			if(rowNum != 0)
				for(int k = 0; k < rowNum; k++)
					tableModel.removeRow(0);
			
			rowNum = 0;
			
			if(cbb_isApproval.getSelectedIndex() == 1){
				try {
					getApprovalData(cbb_sort.getSelectedIndex());
				} catch (RemoteException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}else if(cbb_isApproval.getSelectedIndex() == 2){
				try {
					getPassData(cbb_sort.getSelectedIndex());
				} catch (RemoteException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}else if(cbb_isApproval.getSelectedIndex() == 3){
				try {
					getFailureData(cbb_sort.getSelectedIndex());
				} catch (RemoteException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}else{
				try {
					getApprovalData(cbb_sort.getSelectedIndex());
				} catch (RemoteException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				try {
					getPassData(cbb_sort.getSelectedIndex());
				} catch (RemoteException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				try {
					getFailureData(cbb_sort.getSelectedIndex());
				} catch (RemoteException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			if(rowNum == 0){
				WarningFrame wf = new WarningFrame("目前没有符合条件的单据!");
				wf.setVisible(true);
			}else{
				WarningFrame wf = new WarningFrame("共有 "+ rowNum + " 条单据符合条件!");
				wf.setVisible(true);
			}
		}
		
	}
	
	private void getApprovalData(int index) throws RemoteException{
		
		ApprovalShow controller = new ApprovalShow();
		ArrayList<PurchaseVO> approval_pur = controller.ShowApproving().purchaseVOs;
		ArrayList<SalesVO> approval_sale = controller.ShowApproving().salesVOs;
		ArrayList<AccountBillVO> approval_account = controller.ShowApproving().accountBillVOs;
		ArrayList<InventoryBillVO> approval_inventory = controller.ShowApproving().inventoryBillVOs;
		ArrayList<CashBillVO> approval_cashBill = controller.ShowApproving().cashBillVOs;
		
		model = (DefaultTableModel) table.getModel();
		
		if(index == 0)
			for(int i = 1; i <= 5; i++)
				getApprovalData(i);
		
		if(index == 1)
			for(int i = 0; i < approval_pur.size(); i++){
				Object[] rowData = {null, "进货类单据", approval_pur.get(i).ID, "未审批"};
				model.addRow(rowData);
				listPool.add(approval_pur.get(i));
				typePool.add(approval_pur.get(i).type);
				rowNum++;
			}
		
		if(index == 2)
			for(int i = 0; i < approval_sale.size(); i++){
				Object[] rowData = {new Boolean(false), "销售类单据", approval_sale.get(i).ID, "未审批"};
				model.addRow(rowData);
				listPool.add(approval_sale.get(i));
				typePool.add(approval_sale.get(i).type);
				rowNum++;
			}
		
		if(index == 3)
			for(int i = 0; i < approval_account.size(); i++){
				Object[] rowData = {new Boolean(false), "财务类单据", approval_account.get(i).ID, "未审批"};
				model.addRow(rowData);
				listPool.add(approval_account.get(i));
				typePool.add(approval_account.get(i).type);
				rowNum++;
			}
		
		if(index == 4)
			for(int i = 0; i < approval_inventory.size(); i++){
				Object[] rowData = {new Boolean(false), "库存类单据", approval_inventory.get(i).ID, "未审批"};
				model.addRow(rowData);
				listPool.add(approval_inventory.get(i));
				typePool.add(approval_inventory.get(i).billType);
				rowNum++;
			}
		
		if(index == 5)
			for(int i = 0; i < approval_cashBill.size(); i++){
				Object[] rowData = {new Boolean(false), "现金类单据", approval_cashBill.get(i).ID, "未审批"};
				model.addRow(rowData);
				listPool.add(approval_cashBill.get(i));
				typePool.add(BillType.CASH);
				rowNum++;
			}
	}
	
	private void getPassData(int index) throws RemoteException{
		ApprovalShow controller = new ApprovalShow();
		ArrayList<PurchaseVO> approval_pur = controller.showPass().purchaseVOs;
		ArrayList<SalesVO> approval_sale = controller.showPass().salesVOs;
		ArrayList<AccountBillVO> approval_account = controller.showPass().accountBillVOs;
		ArrayList<InventoryBillVO> approval_inventory = controller.showPass().inventoryBillVOs;
		ArrayList<CashBillVO> approval_cashBill = controller.showPass().cashBillVOs;
		
		model = (DefaultTableModel) table.getModel();
		
		if(index == 0)
			for(int i = 1; i <= 5; i++)
				getPassData(i);
		
		if(index == 1)
			for(int i = 0; i < approval_pur.size(); i++){
				Object[] rowData = {new Boolean(false), "进货类单据", approval_pur.get(i).ID, "已通过"};
				model.addRow(rowData);
				rowNum++;
			}
		
		if(index == 2)
			for(int i = 0; i < approval_sale.size(); i++){
				Object[] rowData = {new Boolean(false), "销售类单据", approval_sale.get(i).ID, "已通过"};
				model.addRow(rowData);
				rowNum++;
			}
		
		if(index == 3)
			for(int i = 0; i < approval_account.size(); i++){
				Object[] rowData = {new Boolean(false), "财务类单据", approval_account.get(i).ID, "已通过"};
				model.addRow(rowData);
				rowNum++;
			}
		
		if(index == 4)
			for(int i = 0; i < approval_inventory.size(); i++){
				Object[] rowData = {new Boolean(false), "库存类单据", approval_inventory.get(i).ID, "已通过"};
				model.addRow(rowData);
				rowNum++;
			}
		
		if(index == 5)
			for(int i = 0; i < approval_cashBill.size(); i++){
				Object[] rowData = {new Boolean(false), "现金类单据", approval_cashBill.get(i).ID, "已通过"};
				model.addRow(rowData);
				rowNum++;
			}
	}
	
	private void getFailureData(int index) throws RemoteException{
		ApprovalShow controller = new ApprovalShow();
		ArrayList<PurchaseVO> approval_pur = controller.showFailure().purchaseVOs;
		ArrayList<SalesVO> approval_sale = controller.showFailure().salesVOs;
		ArrayList<AccountBillVO> approval_account = controller.showFailure().accountBillVOs;
		ArrayList<InventoryBillVO> approval_inventory = controller.showFailure().inventoryBillVOs;
		ArrayList<CashBillVO> approval_cashBill = controller.showFailure().cashBillVOs;
		
		model = (DefaultTableModel) table.getModel();
		
		if(index == 0)
			for(int i = 1; i <= 5; i++)
				getFailureData(i);
		
		if(index == 1)
			for(int i = 0; i < approval_pur.size(); i++){
				Object[] rowData = {new Boolean(false), "进货类单据", approval_pur.get(i).ID, "未通过"};
				model.addRow(rowData);
				listPool.add(approval_pur.get(i));
				rowNum++;
			}
		
		if(index == 2)
			for(int i = 0; i < approval_sale.size(); i++){
				Object[] rowData = {new Boolean(false), "销售类单据", approval_sale.get(i).ID, "未通过"};
				model.addRow(rowData);
				listPool.add(approval_sale.get(i));
				rowNum++;
			}
		
		if(index == 3)
			for(int i = 0; i < approval_account.size(); i++){
				Object[] rowData = {new Boolean(false), "财务类单据", approval_account.get(i).ID, "未通过"};
				model.addRow(rowData);
				listPool.add(approval_account.get(i));
				rowNum++;
			}
		
		if(index == 4)
			for(int i = 0; i < approval_inventory.size(); i++){
				Object[] rowData = {new Boolean(false), "库存类单据", approval_inventory.get(i).ID, "未通过"};
				model.addRow(rowData);
				listPool.add(approval_inventory.get(i));
				rowNum++;
			}
		
		if(index == 5)
			for(int i = 0; i < approval_cashBill.size(); i++){
				Object[] rowData = {new Boolean(false), "现金类单据", approval_cashBill.get(i).ID, "未通过"};
				model.addRow(rowData);
				listPool.add(approval_cashBill.get(i));
				rowNum++;
			}
	}
	
	private void initTable(){
		ApproveTableModel tablemodel = new ApproveTableModel();
		
		table = new JTable(tablemodel);
		table.setCellSelectionEnabled(false);
		
		DefaultTableCellRenderer tcr = new DefaultTableCellRenderer();
		tcr.setHorizontalAlignment(JLabel.CENTER);
		table.setDefaultRenderer(Object.class, tcr);
		
		JTableHeader head = table.getTableHeader();
		head.setForeground(foreColor);
		head.setBackground(backColor);
		
		TableColumn firsetColumn = table.getColumnModel().getColumn(0);
		firsetColumn.setPreferredWidth(30);
		firsetColumn.setMaxWidth(30);
		firsetColumn.setMinWidth(30);
		
		table.getColumnModel().getColumn(1).setPreferredWidth(110);
		table.getColumnModel().getColumn(1).setMaxWidth(110);
		table.getColumnModel().getColumn(1).setMinWidth(110);
		
		table.getColumnModel().getColumn(2).setPreferredWidth(180);
		table.getColumnModel().getColumn(2).setMaxWidth(180);
		table.getColumnModel().getColumn(2).setMinWidth(180);
		
		JScrollPane jsp = new JScrollPane(table);
		jsp.setBounds(85, 95, 380, 465);
		jsp.getViewport().setBackground(new Color(0,0,0,0.3f));
		jsp.setOpaque(false);
		jsp.setVisible(true);
		this.add(jsp);	
	}

}
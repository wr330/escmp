package com.common;



import com.jacob.activeX.ActiveXComponent;
import com.jacob.com.Dispatch;
import com.jacob.com.Variant;

public class WriteDocument {
	// 代表一个word程序
	private ActiveXComponent MsWord = null;
	// 代表进行处理的word文档
	private Dispatch document = null;
	private Dispatch selection = null;

	public WriteDocument() {
		// 开启一个word，如果还没打开的
		if (MsWord == null) {
			MsWord = new ActiveXComponent("Word.Application");
		}
	}

	public void setVisible(boolean visible) {
		MsWord.setProperty("visible", new Variant(visible));
	}

	/**
	 * 创建新文档并生成页面格式
	 */
	public void createNewDocument() {
		Dispatch documents = Dispatch.get(MsWord, "Documents").toDispatch();
		document = Dispatch.call(documents, "Add").toDispatch();
		Dispatch pageSetup = Dispatch.get(document, "PageSetup").toDispatch();
		Dispatch.put(pageSetup, "Orientation", 1);
		Dispatch.put(pageSetup, "TopMargin", 31.5);
		Dispatch.put(pageSetup, "BottomMargin", 47);
		Dispatch.put(pageSetup, "LeftMargin", 72);
		Dispatch.put(pageSetup, "RightMargin", 72);
	}

	/**
	 *  打开一个存在的文档并用document引用
	 * @param wordFilePath
	 */
	public void openFile(String wordFilePath) {
		Dispatch documents = Dispatch.get(MsWord, "Documents").toDispatch();
		document = Dispatch.call(document, "open", wordFilePath,
				new Variant(true), new Variant(false)).toDispatch();

	}

	/**
	 * 向document中插入标题目录
	 * @param textToInsert
	 */
	public void insertDirectory(String textToInsert) {
		selection = Dispatch.get(MsWord, "Selection").toDispatch();
		Dispatch.call(selection, "MoveRight", new Variant(1), new Variant(1));

		Dispatch.put(selection, "Text", textToInsert);// 插入文本
		Dispatch font = Dispatch.get(selection, "Font").toDispatch();
		Dispatch.put(font, "Bold", new Variant(true));
		// Dispatch.put(font, "Italic", new Variant(true));
		Dispatch.put(font, "Name", new Variant("宋体"));
		Dispatch.put(font, "Size", new Variant(20));

		Dispatch.call(selection, "MoveRight", new Variant(1), new Variant(1));

	}



	/**
	 * 设置样式
	 * @param a=1:置中2:靠右3:靠左
	 */
	public void setAlignment(int a) {
		selection = Dispatch.get(MsWord, "Selection").toDispatch();
		Dispatch alignment = Dispatch.get(selection, "ParagraphFormat")
				.toDispatch();
		Dispatch.put(alignment, "Alignment", new Variant(a));
	}

	


	/**
	 *  打开一个已存在的文档
	 * @param docPath
	 */
	public void openDocument(String docPath) {
		// closeDocument();
		Dispatch documents = Dispatch.get(MsWord, "Documents").toDispatch();
		document = Dispatch.call(documents, "Open", docPath).toDispatch();
		selection = Dispatch.get(MsWord, "Selection").toDispatch();
	}

	// 保存文档
	public void save() {
		Dispatch.call(document, "Save");
	}
	public void save(String savePath) {	
		Dispatch.call(Dispatch.call(MsWord, "WordBasic").getDispatch(),"FileSaveAs", savePath);//另存为 
	}
	/**
	 *  保存文档
	 * @param docpath
	 */
	/*public void saveAs(String docpath) {
		Dispatch.call(document, "SaveAs",new Variant(docpath));
	}*/
	 public void saveAs(String docpath) {
			Dispatch.call(document, "SaveAs",new Variant(docpath));
	}
	 
	/**
	  * 保存Word文档到指定的目录(包括文件名)
	  * @param filePath
	  */
	public void saveWordFile(final String filePath) {
	    //保存文件
	       Dispatch.invoke(document, "SaveAs", Dispatch.Method, new Object[] {filePath, new Variant(12)}, new int[1]);
	       //作为word格式保存到目标文件
	       Variant f = new Variant(false);
	       Dispatch.call(document, "Close", f);
	}
	//关闭文档
	public void closeDocument() {
		Dispatch.call(document, "Close", new Variant(0));
		document = null;
	}
	//关闭应用程序
	public void closeWord() {
		Dispatch.call(MsWord, "Quit");
		MsWord = null;
		document = null;
	}
/**
 * 向文档中插入标题结构
 * @param text
 * @param titleLevel
 */
	public void inserTitle(String text, String titleLevel) {
		//Dispatch selection = Dispatch.get(MsWord, "Selection").toDispatch();
		Dispatch.call(selection, "TypeParagraph");
		Dispatch.call(selection, "TypeText", text);
		Dispatch activeDoc = MsWord.getProperty("ActiveDocument").toDispatch();
		Dispatch style = Dispatch.call(activeDoc, "Styles", titleLevel)
				.toDispatch();
		Dispatch.put(selection, "Style", style);

	}
	

	/**
	 *  查找文本
	 * @param toFindText
	 * @return
	 */
	public boolean find(String toFindText) {
		if (toFindText == null || toFindText.equals("")) {
			return false;
		}
		Dispatch selection = Dispatch.get(MsWord, "Selection").toDispatch();// 输入内容需要的对象
		Dispatch find = Dispatch.call(selection, "Find").toDispatch();
		Dispatch.put(find, "Text", toFindText);
		Dispatch.put(find, "Forward", "True");
		Dispatch.put(find, "Format", "True");
		Dispatch.put(find, "MatchCase", "True");
		Dispatch.put(find, "MatchWholeWord", "True");
		return Dispatch.call(find, "Execute").getBoolean();
	}

	/**
	 * 替换文本
	 * @param toFindText
	 * @param newText
	 * @return
	 */
	public boolean replaceText(String toFindText, String newText) {
		if (!find(toFindText)) {
			return false;
		} else {
			Dispatch selection = Dispatch.get(MsWord, "Selection").toDispatch();
			Dispatch.put(selection, "Text", newText);
			return true;
		}

	}

	/**
	 *  创建表格
	 * @param tableTitle:原意是表格名称，经改造，成为判断是何种表格的字符
	 * @param row
	 * @param column
	 */
	public void insertTable(String tableTitle, int row, int column) {
		Dispatch selection = Dispatch.get(MsWord, "Selection").toDispatch();
		// Dispatch.call(selection, "TypeText", tableTitle);
		Dispatch.call(selection, "TypeParagraph");// 空一行
		Dispatch.call(selection, "TypeParagraph");
		Dispatch.call(selection, "MoveDown");// 游标下移一行

		// 建立表格
		Dispatch tables = Dispatch.get(document, "Tables").toDispatch();
		// int count
		// =Dispatch.get(tables,"Count").changeType(Variant.VariantInt).getInt();//查询document中的表格数量
		// Dispatch table=Dispatch.call(tables,"Item",new
		// Variant(1)).toDispatch()//文档中的第一个表格
		Dispatch range = Dispatch.get(selection, "Range").toDispatch();// 当前光标位置或选中的区域
		Dispatch newTable = Dispatch.call(tables, "Add", range,
				new Variant(row), new Variant(column), new Variant(1))
				.toDispatch();// 设置行列和表格外框宽度
		Dispatch cols = Dispatch.get(newTable, "Columns").toDispatch(); // 此表的所有列，

		int colCount = Dispatch.get(cols, "Count")
				.changeType(Variant.VariantInt).getInt();// 一共有多少列
															
		// System.out.println(colCount + "列"); // 实际上这个数==column
		if (tableTitle.equals("表格")) {
			putTxtToCell(newTable, 1, 1, "序号");
			setFont(true, false, false, "0", "15", "宋体");
			putTxtToCell(newTable, 1, 2, "机号");
			setFont(true, false, false, "0", "15", "宋体");
			putTxtToCell(newTable, 1, 3, "首滑日期");
			setFont(true, false, false, "0", "15", "宋体");
			putTxtToCell(newTable, 1, 4, "首飞日期");
			setFont(true, false, false, "0", "15", "宋体");
			putTxtToCell(newTable, 1, 5, "2014/2015飞行架次");
			setFont(true, false, false, "0", "15", "宋体");
			putTxtToCell(newTable, 1, 6, "总飞行架次");
			setFont(true, false, false, "0", "15", "宋体");
			putTxtToCell(newTable, 1, 7, "总飞行时间");
			setFont(true, false, false, "0", "15", "宋体");
			putTxtToCell(newTable, 1, 8, "滑行总次数");
			setFont(true, false, false, "0", "15", "宋体");
			putTxtToCell(newTable, 1, 9, "备 注");
			setFont(true, false, false, "0", "15", "宋体");
		}
		if (tableTitle.equals("表格1")) {
			putTxtToCell(newTable, 1, 1, "项目");
			setFont(true, false, false, "0", "15", "宋体");
			putTxtToCell(newTable, 1, 2, "机号");
			setFont(true, false, false, "0", "15", "宋体");
			putTxtToCell(newTable, 1, 3, "主要内容");
			setFont(true, false, false, "0", "15", "宋体");
			putTxtToCell(newTable, 1, 4, "备注");
			setFont(true, false, false, "0", "15", "宋体");
			putTxtToCell(newTable, 2, 1, "现\r\n场\r\n情\r\n况");
			setFont(true, false, false, "0", "15", "宋体");
			putTxtToCell(newTable, 4, 1, "其他");
			setFont(true, false, false, "0", "15", "宋体");
		}
		for (int i = 1; i <= colCount; i++) { // 循环取出每一列
			Dispatch col = Dispatch.call(cols, "Item", new Variant(i))
					.toDispatch();
			Dispatch cells = Dispatch.get(col, "Cells").toDispatch();// 当前列中单元格
			int cellCount = Dispatch.get(cells, "Count")
					.changeType(Variant.VariantInt).getInt();// 当前列中单元格数
																// 实际上这个数等于row

			for (int j = 2; j <= cellCount; j++) { // 每一列中的单元格数
				Dispatch cell = Dispatch.call(cells, "Item", new Variant(j))
						.toDispatch(); // 当前单元格
				// Dispatch cell = Dispatch.call(newTable, "Cell", new
				// Variant(j) , new Variant(i) ).toDispatch(); //取单元格的另一种方法
				Dispatch.call(cell, "Select");// 选中当前单元格
				//Dispatch.put(selection, "Text", "第" + j + "行，第" + i + "列");// 輸入文本內容到單元格中
				setFont(true, false, false, "0", "14", "宋体");
				// putTxtToCell(newTable,j,i,"");
			}
		}
		moveEnd();//必须把光标打到最后，不然再进行其他操作时，默认会在表格的最后一个单元中进行

	}

	/** */
	/**
	 * 在当前插入点空一行后插入字符串（字符串可调整字体）
	 * 
	 * @param newText
	 *            要插入的新字符串
	 */
	public void insertRedText(String newText) {
		Dispatch.call(selection, "TypeParagraph");// 空一行
		Dispatch selection = Dispatch.get(MsWord, "Selection").toDispatch();
		Dispatch.put(selection, "Text", newText);
		setFont(true, false, false, "255", "14", "宋体");
		Dispatch.call(selection, "MoveRight", new Variant(1), new Variant(1));
	}
	/**
	 * 在当前插入点空一行后插入字符串（字符串可调整字体）
	 * 
	 * @param newText
	 *            要插入的新字符串
	 */
	public void insertText(String newText) {
		Dispatch.call(selection, "TypeParagraph");// 空一行
		Dispatch selection = Dispatch.get(MsWord, "Selection").toDispatch();
		Dispatch.put(selection, "Text", newText);
		setFont(false, false, false, "0", "14", "宋体");
		Dispatch.call(selection, "MoveRight", new Variant(1), new Variant(1));
	}


	/**
	 * 向单元格制定cell中插入文本
	 * @param table
	 * @param cellRowIdx
	 * @param cellColIdx
	 * @param txt
	 */
	public void putTxtToCell(Dispatch table, int cellRowIdx, int cellColIdx,
			String txt) {
		Dispatch cell = Dispatch.call(table, "Cell", new Variant(cellRowIdx),
				new Variant(cellColIdx)).toDispatch();
		Dispatch.call(cell, "Select");
		Dispatch selection = Dispatch.get(MsWord, "Selection").toDispatch();
		Dispatch.put(selection, "Text", txt);
	}

	public void moveEnd() {
		if (selection == null)
			selection = Dispatch.get(document, "Selection").toDispatch();
		Dispatch.call(selection, "EndKey", new Variant(6));
	}

	/** */
	/**
	 * 在第1行前增加一行
	 * 
	 * @param tableIndex
	 *            word文档中的第N张表(从1开始)
	 */
	public void addFirstTableRow(int tableIndex,String[] args) {
		// 所有表格
		Dispatch tables = Dispatch.get(document, "Tables").toDispatch();
		// 要填充的表格
		Dispatch table = Dispatch.call(tables, "Item", new Variant(tableIndex))
				.toDispatch();
		// 表格的所有行
		Dispatch rows = Dispatch.get(table, "Rows").toDispatch();
		Dispatch row = Dispatch.get(rows, "First").toDispatch();
		Dispatch.call(rows, "Add", new Variant(row));
		
		for(int i=0;i<args.length;i++){
		putTxtToCell(table, 1, i+1, args[i]);
		setFont(false, false, false, "0", "14", "宋体");
		}
		moveEnd();
	}
	
	/** */
	/**
	 * 在第i行输入内容
	 * 
	 * @param tableIndex
	 *            word文档中的第N张表(从1开始)
	 */
	public void addContentRow(int tableIndex,String[] args,int rowNum ) {
		// 所有表格
		Dispatch tables = Dispatch.get(document, "Tables").toDispatch();
		// 要填充的表格
		Dispatch table = Dispatch.call(tables, "Item", new Variant(tableIndex))
				.toDispatch();
		
		for(int i=0;i<args.length;i++){
		putTxtToCell(table, rowNum, i+1, args[i]);
		setFont(false, false, false, "0", "14", "宋体");
		}
		moveEnd();
	}

	/** */
	/**
	 * 增加一行
	 * 
	 * @param tableIndex
	 *            word文档中的第N张表(从1开始)
	 */
	public void addRow(int tableIndex) {
		Dispatch tables = Dispatch.get(document, "Tables").toDispatch();
		// 要填充的表格
		Dispatch table = Dispatch.call(tables, "Item", new Variant(tableIndex))
				.toDispatch();
		// 表格的所有行
		Dispatch rows = Dispatch.get(table, "Rows").toDispatch();
		Dispatch.call(rows, "Add");

		// setFont(true,false,false,"0","15","宋体");
	}

	/** */
	/**
	 * 自动调整表格
	 * 
	 */
	public void autoFitTable() {
		Dispatch tables = Dispatch.get(document, "Tables").toDispatch();
		int count = Dispatch.get(tables, "Count").toInt();
		for (int i = 0; i < count; i++) {
			Dispatch table = Dispatch.call(tables, "Item", new Variant(i + 1))
					.toDispatch();
			Dispatch cols = Dispatch.get(table, "Columns").toDispatch();
			Dispatch.call(cols, "AutoFit");
		}
	}

	/** */
	/**
	 * 设置当前选定内容的字体
	 * 
	 * @param boldSize
	 * @param italicSize
	 * @param underLineSize
	 *            下划线
	 * @param colorSize
	 *            字体颜色
	 * @param size
	 *            字体大小
	 * @param name
	 *            字体名称
	 */
	public void setFont(boolean bold, boolean italic, boolean underLine,
			String colorSize, String size, String name) {
		Dispatch font = Dispatch.get(selection, "Font").toDispatch();
		Dispatch.put(font, "Name", new Variant(name));
		Dispatch.put(font, "Bold", new Variant(bold));
		Dispatch.put(font, "Italic", new Variant(italic));
		Dispatch.put(font, "Underline", new Variant(underLine));
		Dispatch.put(font, "Color", colorSize);
		Dispatch.put(font, "Size", size);
	}

	/** */
	/**
	 * 合并单元格
	 * 
	 * @param tableIndex
	 * @param fstCellRowIdx
	 * @param fstCellColIdx
	 * @param secCellRowIdx
	 * @param secCellColIdx
	 */
	public void mergeCell(int tableIndex, int fstCellRowIdx, int fstCellColIdx,
			int secCellRowIdx, int secCellColIdx) {
		// 所有表格
		Dispatch tables = Dispatch.get(document, "Tables").toDispatch();
		// 要填充的表格
		Dispatch table = Dispatch.call(tables, "Item", new Variant(tableIndex))
				.toDispatch();
		Dispatch fstCell = Dispatch.call(table, "Cell",
				new Variant(fstCellRowIdx), new Variant(fstCellColIdx))
				.toDispatch();
		Dispatch secCell = Dispatch.call(table, "Cell",
				new Variant(secCellRowIdx), new Variant(secCellColIdx))
				.toDispatch();
		Dispatch.call(fstCell, "Merge", secCell);
	}

	/** */
	/**
	 * 创建表格
	 * 
	 * @param pos
	 *            位置
	 * @param cols
	 *            列数
	 * @param rows
	 *            行数
	 */
	public void createTable(int numCols, int numRows) {
		
		Dispatch.call(selection, "TypeParagraph");
		Dispatch tables = Dispatch.get(document, "Tables").toDispatch();
		Dispatch range = Dispatch.get(selection, "Range").toDispatch();
		Dispatch newTable = Dispatch.call(tables, "Add", range,
				new Variant(numRows), new Variant(numCols), new Variant(1))
				.toDispatch();
		Dispatch.call(selection, "MoveRight");
		moveEnd();
		
	}

	public static void main(String[] args) {
		WriteDocument wd = new WriteDocument();
		wd.setVisible(true);
		wd.createNewDocument();
		wd.insertDirectory("科研试飞每日简报（20150126）");
		wd.setAlignment(1);
		wd.inserTitle("一、飞行情况（注：红色为当日最新内容）", "标题 2");
		wd.inserTitle("至2015年1月26日，飞机共有  架，其中   已转场试飞院；   J10 在成飞。均已飞行", "正文");
		wd.insertRedText("这里是当日试飞内容");
		wd.inserTitle("(1)01#", "标题 2");
		wd.inserTitle("本机于    日首飞，在成都地区共飞行  个起落。本机总共飞行   个起落", "正文");
		wd.inserTitle("(2)02#", "标题 2");
		wd.inserTitle("正文内容", "正文");
		wd.inserTitle("(3)03#", "标题 2");
		wd.inserTitle("abc", "正文");
		wd.inserTitle("(5)飞行概况", "标题 2");
		wd.insertTable("表格", 4, 9);
		wd.inserTitle("abc", "正文");
		wd.inserTitle("二、 现场工作（注：红色为当日最新内容）", "标题 3");

		wd.insertTable("表格1", 5, 4);
		wd.mergeCell(2, 2, 1, 3, 1);
		wd.mergeCell(2, 4, 1, 5, 1);
		wd.saveAs("E:\\temp1.doc");
		wd.closeDocument();
		wd.closeWord();
		//wd.createTable(3, 2);
	}
}

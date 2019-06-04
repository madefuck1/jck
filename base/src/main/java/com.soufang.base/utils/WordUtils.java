package com.soufang.base.utils;

import java.io.*;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.soufang.base.PropertiesParam;
import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.model.FieldsDocumentPart;
import org.apache.poi.hwpf.usermodel.Field;
import org.apache.poi.hwpf.usermodel.Fields;
import org.apache.poi.hwpf.usermodel.Paragraph;
import org.apache.poi.hwpf.usermodel.Range;
import org.apache.poi.hwpf.usermodel.Table;
import org.apache.poi.hwpf.usermodel.TableCell;
import org.apache.poi.hwpf.usermodel.TableIterator;
import org.apache.poi.hwpf.usermodel.TableRow;

/**
 * @Auther: chen
 * @Date: 2019/5/23
 * @Description:
 */
public class WordUtils {

    public static void main(String[] args)
    {
        Map<String, String> map = new HashMap<String, String>();
        map.put("${sub}", "湖南大学");
        map.put("${item1}", "湖南大学");
        map.put("${item2}", "湖南");
        readwriteWord(map);
    }

    /**
     * 实现对word读取和修改操作
     *
     * @param map
     */
    public static Map<String, Object>  readwriteWord(Map<String, String> map) {
        String srcPath = "C:\\Users\\chen\\Desktop\\fsdownload\\test.doc";
        FileInputStream in = null;
        try {
            in = new FileInputStream(new File(srcPath));
        } catch (FileNotFoundException e1) {
            e1.printStackTrace();
        }
        HWPFDocument hdt = null;
        try {
            hdt = new HWPFDocument(in);
        } catch (IOException e1) {
            e1.printStackTrace();
        }
//        Fields fields = hdt.getFields();
//        Iterator<Field> it = fields.getFields(FieldsDocumentPart.MAIN).iterator();
//        while (it.hasNext()) {
//            System.out.println(it.next().getType());
//        }

        //读取word文本内容
        Range range = hdt.getRange();
        TableIterator tableIt = new TableIterator(range);
        //迭代文档中的表格
        int ii = 0;
        while (tableIt.hasNext()) {
            Table tb = (Table) tableIt.next();
            ii++;
            //迭代行，默认从0开始
            for (int i = 0; i < tb.numRows(); i++) {
                TableRow tr = tb.getRow(i);
                //只读前8行，标题部分
                if(i >=8) break;
                //迭代列，默认从0开始
                for (int j = 0; j < tr.numCells(); j++) {
                    TableCell td = tr.getCell(j);//取得单元格
                    //取得单元格的内容
                    for(int k=0;k<td.numParagraphs();k++){
                        Paragraph para =td.getParagraph(k);
                        String s = para.text();
                    } //end for
                }   //end for
            }   //end for
        } //end while
        // 替换文本内容
        for (Map.Entry<String, String> entry : map.entrySet()) {
            range.replaceText(entry.getKey(), entry.getValue());
        }
        ByteArrayOutputStream ostream = new ByteArrayOutputStream();
        try {
            hdt.write(ostream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 输出字节流
        //out.write(ostream.toByteArray());

        ByteArrayInputStream swapStream = new ByteArrayInputStream(ostream.toByteArray());
        Map<String, Object> result = FtpClient.uploadInvoice(swapStream,"/uploadContract");
        try {
            ostream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return  result;
    }
}

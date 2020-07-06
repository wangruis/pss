package com.shop.pss.excel;


import com.shop.pss.excel.convert.IConvertClass;
import com.shop.pss.excel.model.ConvertDataModel;
import com.shop.pss.excel.model.ExcelConfigModel;
import com.shop.pss.excel.model.ExcelFieldModel;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class ExcelConfigParser {

	private static String path;
	private ExcelConfigParser(){

	}
	private static final String  configFile = "excelmodel.xml";

	private static List<ExcelConfigModel> excelConfigList;
	private static List<ConvertDataModel> convertDataList;
	protected static final Logger logger = LoggerFactory.getLogger(ExcelConfigParser.class);

	private static Map<String,ExcelConfig> cachedExcelMap = new HashMap();
	private static Map<String, IConvertClass> cachedConvertInstanceMap = new HashMap();


	public static ExcelConfig getConfigById(String id){
		loadXmlConfig(path);
		if(cachedExcelMap!=null){
			return cachedExcelMap.get(id);
		}
		return null;
	}

	/**
	 * 根据转换外键id获取缓存区转换数据集
	 * @param id
	 * @return
	 */
	public static IConvertClass getConvertDateById(String id){
		if(excelConfigList==null) {
			loadXmlConfig(path);
		}
		if(cachedConvertInstanceMap!=null && cachedConvertInstanceMap.containsKey(id)){
			return cachedConvertInstanceMap.get(id);
		}else{
			for(ConvertDataModel model : convertDataList){
				if(model.getId().equals(id) && StringUtils.isNotEmpty(model.getInitClass())){
					Class<IConvertClass> initClass;
					try {
						initClass = (Class<IConvertClass>) ExcelConfigParser.class.getClassLoader().loadClass(model.getInitClass());
						IConvertClass convert =  initClass.newInstance();
						return convert;
					} catch (Exception e) {
						System.err.println(e.getMessage());
						e.printStackTrace();
					}
				}
			}
		}
		return null;
	}




	protected static void loadXmlConfig(String filepath) {
		try {
			ClassPathResource classPathResource = new ClassPathResource("excelmodel.xml");

			InputStream inputStream = classPathResource.getInputStream();
			File f = File.createTempFile("excelmodel", ".xml");
			try {
				FileUtils.copyInputStreamToFile(inputStream, f);
			} finally {
				IOUtils.closeQuietly(inputStream);
			}
			if(!f.exists() || !f.canRead()){
				logger.error(configFile+"配置文件读取失败");
				return;
			}
			excelConfigList = new ArrayList<>();
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			Document document = db.parse(f);
			Element element = document.getDocumentElement();
			NodeList configNodes = element.getElementsByTagName("config");
			for (int i = 0; i < configNodes.getLength(); i++) {
				Element configElement = (Element) configNodes.item(i);
				ExcelConfigModel configModel = new ExcelConfigModel();
				configModel.setId(configElement.getAttribute("id"));
				configModel.setUploadType(configElement.getAttribute("uploadType"));
				configModel.setExportFileName(configElement.getAttribute("exportFileName"));
				configModel.setModelClass(configElement.getAttribute("modelclass"));
				configModel.setTreeColIndex(configElement.getAttribute("treeColIndex"));
				NodeList childNodes = configElement.getChildNodes();
				List<ExcelFieldModel> fieldList = null;
				for(int j=0;j<childNodes.getLength();j++){
					if(childNodes.item(j).getNodeType()==Node.ELEMENT_NODE){
						if("fields".equals(childNodes.item(j).getNodeName())){
							fieldList = new ArrayList<ExcelFieldModel>();
							NodeList fieldsNodes = childNodes.item(j).getChildNodes();
							for(int k=0;k<fieldsNodes.getLength();k++){
								if(fieldsNodes.item(k).getNodeType()==Node.ELEMENT_NODE){
									if("field".equals(fieldsNodes.item(k).getNodeName())){
										Element fieldElement = (Element) fieldsNodes.item(k);
										ExcelFieldModel model = new ExcelFieldModel();
										model.setName(fieldElement.getAttribute("name"));
										model.setShowName(fieldElement.getAttribute("show_name"));
										model.setType(fieldElement.getAttribute("type"));
										model.setDefaultVal(fieldElement.getAttribute("default_val"));
										model.setEosPropName(fieldElement.getAttribute("prop_name"));
										model.setParentColIndex(fieldElement.getAttribute("parent_index"));
										model.setIsmult(Boolean.parseBoolean(fieldElement.getAttribute("ismult")));
										model.setNullbale(Boolean.parseBoolean(fieldElement.getAttribute("nullable")));
										model.setConvertid(fieldElement.getAttribute("convertid"));
										model.setUnique(Boolean.parseBoolean(fieldElement.getAttribute("unique")));
										fieldList.add(model);
									}
								}
							}
						}
					}
				}
				configModel.setFields(fieldList);
				cachedExcelMap.put(configModel.getId(), new ExcelConfig(configModel));
				excelConfigList.add(configModel);
			}

			//开始 解析convertdata
			NodeList convertNodes = element.getElementsByTagName("convert");
			convertDataList = new ArrayList<>();
			for (int i = 0; i < convertNodes.getLength(); i++) {
				Element converetElement = (Element) convertNodes.item(i);
				ConvertDataModel convertModel = new ConvertDataModel();
				convertModel.setId(converetElement.getAttribute("id"));
				convertModel.setInitClass(converetElement.getAttribute("initClass"));

				Class<IConvertClass> initClass;
				try {
					initClass = (Class<IConvertClass>) ExcelConfigParser.class.getClassLoader().loadClass(convertModel.getInitClass());
					IConvertClass convert =  initClass.newInstance();
					cachedConvertInstanceMap.put(convertModel.getId(), convert);
				} catch (Exception e) {
					System.err.println(e.getMessage());
					e.printStackTrace();
				}
				convertDataList.add(convertModel);
			}
		} catch (FileNotFoundException e) {
			System.err.println(e.getMessage());
		} catch (ParserConfigurationException e) {
			System.err.println(e.getMessage());
		} catch (SAXException e) {
			System.err.println(e.getMessage());
		} catch (IOException e) {
			System.err.println(e.getMessage());
		}
	}

}

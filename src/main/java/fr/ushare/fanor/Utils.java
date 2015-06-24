package fr.ushare.fanor;

import java.awt.Color;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.io.File;
import java.security.MessageDigest;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import net.minecraft.client.Minecraft;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

/**
 * This file (c) by : - Mickael VINET alias fanor79
 *
 * This file is licensed under a
 * GNU GENERAL PUBLIC LICENSE V3.0
 *
 * See the LICENSE file to learn more.
 *
 * If you have contributed to this file, add your name to authors list.
*/
public class Utils {

	/**
	 * Copy text to clipboard
	 * @param text
	 */
	public static void Copier(String text)
	{
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		Clipboard clip = toolkit.getSystemClipboard();
		clip.setContents(new StringSelection(text), null);
	}
	
	/**
	 * @return path for config files
	 */
	public static String getConfig()
	{
		return Minecraft.getMinecraft().mcDataDir + "//Ushare//Config//config.xml";
	}

	/**
	 * Create the path for the dir config and create config.xml
	 */
	public static void GenererConfig()
	{
		File dirUshareConfig = new File(Minecraft.getMinecraft().mcDataDir + "//Ushare//Config//");
		File xmlConfig = new File(getConfig());

		if(!dirUshareConfig.exists())
		{
			dirUshareConfig.mkdirs();
		}

		if(!xmlConfig.exists())
		{
			try{
				DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
				DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

				// root elements
				Document doc = docBuilder.newDocument();
				Element rootElement = doc.createElement("Config");
				doc.appendChild(rootElement);

				// setting elements
				Element setting = doc.createElement("Setting");
				rootElement.appendChild(setting);

				// opacity elements
				Element opacity = doc.createElement("transparency");
				opacity.appendChild(doc.createTextNode("0.0"));
				setting.appendChild(opacity);

				// color elements
				Element color = doc.createElement("color");
				color.appendChild(doc.createTextNode("none"));
				setting.appendChild(color);

				// write the content into xml file
				TransformerFactory transformerFactory = TransformerFactory.newInstance();
				Transformer transformer = transformerFactory.newTransformer();
				DOMSource source = new DOMSource(doc);
				StreamResult result = new StreamResult(xmlConfig);

				transformer.transform(source, result);

			} catch (ParserConfigurationException pce) {
				pce.printStackTrace();
			} catch (TransformerConfigurationException e) {
				e.printStackTrace();
			} catch (TransformerException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * return Document of XML
	 */
	public static Document readXml(String xml) throws Exception
	{
		final DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		final DocumentBuilder builder = factory.newDocumentBuilder();
		final Document document= builder.parse(new File(xml));
		return document;
	}

	/**
	 * return the value of field 
	 */
	public static String getSetting(String field) throws Exception
	{
		Document doc = readXml(getConfig());
		Element racine = doc.getDocumentElement();
		
		final NodeList racineNoeuds = racine.getChildNodes();
		final int nbRacineNoueds = racineNoeuds.getLength();
		String res = "";

		for(int i = 0; i < nbRacineNoueds; i++)
		{
			if(racineNoeuds.item(i).getNodeType() == Node.ELEMENT_NODE)
			{
				final Element setting = (Element) racineNoeuds.item(i);
				
				try
				{
					final Element val = (Element)setting.getElementsByTagName(field).item(0);
					res = val.getTextContent();	
				}
				catch(Exception e)
				{
					Element newElement = doc.createElement(field);
					newElement.appendChild(doc.createTextNode("null"));
					setting.appendChild(newElement);
				}

			}
		}

		return res;
	}

	/**
	 * Allow change the value of field
	 */
	public static void setSetting(String field, String value) throws Exception
	{
		Document doc = readXml(getConfig());
		Element racine = doc.getDocumentElement();

		final NodeList racineNoeuds = racine.getChildNodes();
		final int nbRacineNoueds = racineNoeuds.getLength();

		for(int i = 0; i < nbRacineNoueds; i++)
		{
			if(racineNoeuds.item(i).getNodeType() == Node.ELEMENT_NODE)
			{
				final Element setting = (Element) racineNoeuds.item(i);
				try
				{
					final Element val = (Element)setting.getElementsByTagName(field).item(0);
					val.setTextContent(value);	
				}catch(Exception e)
				{
					Element newElement = doc.createElement(field);
					newElement.appendChild(doc.createTextNode(value));
					setting.appendChild(newElement);
				}
			}
		}

		saveXML(doc, getConfig());

	}

	/**
	 * save the xml with the document in inpout, and the field in output 
	 */
	public static void saveXML(Document input, String output) throws TransformerFactoryConfigurationError, TransformerException
	{
		Transformer transformer = TransformerFactory.newInstance().newTransformer();
		Result o = new StreamResult(output);
		Source i = new DOMSource(input);

		transformer.transform(i, o);

	}

	/**
	 * return the Color of the parametre
	 */
	public static Color getColor(String color)
	{
		if(color.equalsIgnoreCase("red"))
			return Color.RED;
		else if(color.equalsIgnoreCase("green"))
			return Color.GREEN;
		else if(color.equalsIgnoreCase("blue"))
			return Color.BLUE;
		return null;
	}

	/**
	 * convert password to SHA512
	 */
	public static String toSHA256(String password) throws Exception
	{
		MessageDigest md = MessageDigest.getInstance("SHA-256");
		md.update(password.getBytes());
		
		byte byteData[] = md.digest();
		
		StringBuffer sb = new StringBuffer();
		for(int i = 0; i < byteData.length; i++)
		{
			sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
		}
	
		return sb.toString();
	}
	
	/**
	 * Convert String to JSON
	 */
	public static JsonObject toJSON(String text)
	{
		JsonObject jsonObject = (new JsonParser().parse(text).getAsJsonObject());
		return jsonObject;
	}

}

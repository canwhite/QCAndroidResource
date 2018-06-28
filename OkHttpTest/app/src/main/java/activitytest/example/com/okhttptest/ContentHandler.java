package activitytest.example.com.okhttptest;

import android.util.Log;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class ContentHandler extends DefaultHandler {

    private static final String TAG = "ContentHandler";

    private String nodeName;
    private StringBuilder id;
    private StringBuilder name;
    private StringBuilder version;

    @Override
    public void startDocument() throws SAXException {

        //初始化数据
        id = new StringBuilder();
        name = new StringBuilder();
        version = new StringBuilder();



    }


    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {

        nodeName = localName;

    }



    //
    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {

        if ("id".equals(nodeName)){

            id.append(ch,start,length);

        }else if ("name".equals(nodeName)){

            name.append(ch,start,length);

        }else if ("version".equals(nodeName)){

            version.append(ch,start,length);
        }


    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {

        if ("app".equals(localName)){

            Log.d(TAG, "id is" + id.toString().trim());
            Log.d(TAG, "name is" + name.toString().trim());
            Log.d(TAG, "version is" + version.toString().trim());

            //将StringBuilder清空掉
            id.setLength(0);
            name.setLength(0);
            version.setLength(0);
        }
    }

    @Override
    public void endDocument() throws SAXException {
        //如果内部不重写方法，就直接调用父类实现
        super.endDocument();
    }


}

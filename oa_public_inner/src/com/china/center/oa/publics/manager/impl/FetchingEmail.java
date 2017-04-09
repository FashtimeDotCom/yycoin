package com.china.center.oa.publics.manager.impl;

/**
 * Created by user on 2016/4/8.
 */
import com.sun.mail.imap.IMAPMessage;

import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeUtility;
import javax.mail.search.FlagTerm;
import java.io.*;
import java.security.Security;
import java.util.Enumeration;
import java.util.Properties;


public class FetchingEmail {

    private static final String IMAP = "imap";

    public static void main(String[] args) throws Exception{
        receiveEmail("imap.163.com", "yycoindd@163.com", "yycoin1234");
//        receiveEmail("imap.163.com", "xxx@163.com", "xxx");
//        receiveEmail("imap.qq.com","xxx@qq.com", "xxx");
//        receiveEmail("imap.mail.me.com", "xxx@icloud.com", "xxx");
//        receiveEmail("imap.sina.com", "xxx@sina.com", "xxx");
//        receiveEmailWithPop("pop3.qq.com", "xxx@qq.com", "xxx");
    }

    public static void receiveEmail(String host, String username, String password) throws Exception {
        String port = "993";
        Security.addProvider(new com.sun.net.ssl.internal.ssl.Provider());
        final String SSL_FACTORY = "javax.net.ssl.SSLSocketFactory";

        Properties props = System.getProperties();
        props.setProperty("mail.imap.socketFactory.class", SSL_FACTORY);
        props.setProperty("mail.imap.socketFactory.fallback", "false");
        props.setProperty("mail.imap.socketFactory.port", port);
        props.setProperty("mail.smtp.starttls.enable", "true");

        props.setProperty("mail.store.protocol", "imap");
        props.setProperty("mail.imap.host", host);
        props.setProperty("mail.imap.port", port);
        props.setProperty("mail.imap.ssl.enable", "true");
        props.setProperty("mail.imap.auth.plain.disable", "true");
        props.setProperty("mail.imap.auth.login.disable", "true");

        //You must add these two settings, otherwize large attachment will not be downloaded
        props.put("mail.imap.partialfetch", "true");
        props.put("mail.imap.fetchsize", "819200");
        Session session = Session.getDefaultInstance(props, null);
//        session.setDebug(true);
        Store store = session.getStore(IMAP);
        Folder inbox = null;

        try {
            store.connect(host, username, password);
            inbox = store.getFolder("Inbox");
            inbox.open(Folder.READ_ONLY);
            FetchProfile profile = new FetchProfile();
            profile.add(FetchProfile.Item.ENVELOPE);
//            Message[] messages = inbox.getMessages();
            //only receive unread mails
            Message messages[] = inbox.search(new FlagTerm(new Flags(
                    Flags.Flag.SEEN), false));
            inbox.fetch(messages, profile);
            System.out.println("***mail count***" + messages.length);
            System.out.println("***unread mail count***" + inbox.getUnreadMessageCount());

            IMAPMessage msg;
            for (Message message : messages) {
                msg = (IMAPMessage) message;
                Flags flags = message.getFlags();
                if (flags.contains(Flags.Flag.SEEN)){
                    System.out.println("这是一封已读邮件");
                    continue;
                }
                else {
                    System.out.println("未读邮件");
                }
                String from = decodeText(msg.getFrom()[0].toString());
                InternetAddress ia = new InternetAddress(from);
                System.out.println("FROM:" + ia.getPersonal() + '(' + ia.getAddress() + ')');
                System.out.println("TITLE:" + msg.getSubject());
                System.out.println("SIZE:" + msg.getSize());
                System.out.println("DATE:" + msg.getSentDate());
                System.out.println("Content:" + msg.getContent());
                System.out.println("ContentType:" + msg.getContentType());
                Enumeration headers = msg.getAllHeaders();
                System.out.println("----------------------allHeaders-----------------------------");
                while (headers.hasMoreElements()) {
                    Header header = (Header) headers.nextElement();
                    System.out.println(header.getName() + " ======= " + header.getValue());
                }
                parseMultipart(msg.getContent());
//                msg.setFlag(Flags.Flag.SEEN, true);
                System.out.println("***finished***");
//                String filename = "d:/temp/" + decodeText(msg.getSubject());
//                System.out.println(filename);
//                saveParts(msg.getContent(), filename);
            }
        } catch(Exception e){
           e.printStackTrace();
        } finally {
            try {
                if (inbox != null) {
                    inbox.close(false);
                }
            } catch (Exception ignored) {
            }
            try {
                store.close();
            } catch (Exception ignored) {
            }
        }
    }

    public static void receiveEmailWithPop(String host, String username, String password) throws Exception {
        String port = "995";
        Security.addProvider(new com.sun.net.ssl.internal.ssl.Provider());
        final String SSL_FACTORY = "javax.net.ssl.SSLSocketFactory";

        Properties props = System.getProperties();
        props.setProperty("mail.pop3.socketFactory.class", SSL_FACTORY);
        props.setProperty("mail.pop3.socketFactory.fallback", "false");
        props.setProperty("mail.pop3.socketFactory.port", port);
//        props.setProperty("mail.smtp.starttls.enable", "true");

        props.setProperty("mail.store.protocol", "pop3");
        props.setProperty("mail.pop3.host", host);
        props.setProperty("mail.pop3.port", port);
        props.setProperty("mail.pop3.ssl.enable", "true");
        props.setProperty("mail.pop3.auth.plain.disable", "true");
        props.setProperty("mail.pop3.auth.login.disable", "true");

        //You must add these two settings, otherwize large attachment will not be downloaded
        props.put("mail.imap.partialfetch", "true");
        props.put("mail.imap.fetchsize", "819200");
        Session session = Session.getDefaultInstance(props, null);
//        session.setDebug(true);
        Store store = session.getStore(IMAP);
        Folder inbox = null;

        try {
            store.connect(host, username, password);
            inbox = store.getFolder("Inbox");
            inbox.open(Folder.READ_ONLY);
            FetchProfile profile = new FetchProfile();
            profile.add(FetchProfile.Item.ENVELOPE);
//            Message[] messages = inbox.getMessages();
            //only receive unread mails
            Message messages[] = inbox.search(new FlagTerm(new Flags(
                    Flags.Flag.SEEN), false));
            inbox.fetch(messages, profile);
            System.out.println("***mail count***" + messages.length);
            System.out.println("***unread mail count***" + inbox.getUnreadMessageCount());

            IMAPMessage msg;
            for (Message message : messages) {
                msg = (IMAPMessage) message;
                Flags flags = message.getFlags();
                if (flags.contains(Flags.Flag.SEEN)){
                    System.out.println("这是一封已读邮件");
                    continue;
                }
                else {
                    System.out.println("未读邮件");
                }
                String from = decodeText(msg.getFrom()[0].toString());
                InternetAddress ia = new InternetAddress(from);
                System.out.println("FROM:" + ia.getPersonal() + '(' + ia.getAddress() + ')');
                System.out.println("TITLE:" + msg.getSubject());
                System.out.println("SIZE:" + msg.getSize());
                System.out.println("DATE:" + msg.getSentDate());
                System.out.println("Content:" + msg.getContent());
                System.out.println("ContentType:" + msg.getContentType());
                Enumeration headers = msg.getAllHeaders();
                System.out.println("----------------------allHeaders-----------------------------");
                while (headers.hasMoreElements()) {
                    Header header = (Header) headers.nextElement();
                    System.out.println(header.getName() + " ======= " + header.getValue());
                }
                parseMultipart(msg.getContent());
                msg.setFlag(Flags.Flag.SEEN,true);
                System.out.println("***finished***");
//                String filename = "d:/temp/" + decodeText(msg.getSubject());
//                System.out.println(filename);
//                saveParts(msg.getContent(), filename);
            }
        } catch(Exception e){
            e.printStackTrace();
        } finally {
            try {
                if (inbox != null) {
                    inbox.close(false);
                }
            } catch (Exception ignored) {
            }
            try {
                store.close();
            } catch (Exception ignored) {
            }
        }
    }

    static String decodeText(String text) throws UnsupportedEncodingException {
        if (text == null)
            return null;
//        if (text.startsWith("=?GB") || text.startsWith("=?gb"))
        if (text.indexOf("=?GB")!=-1 || text.indexOf("=?gb")!= -1)
//            text = MimeUtility.decodeText(text);
            text = MimeUtility.encodeText(text);
        else
            text = new String(text.getBytes("ISO8859_1"));
        System.out.println("****text***"+text);
        return text;
    }


    /**
     * �Ը����ʼ��Ľ���
     *
     * @param content
     * @throws MessagingException
     * @throws IOException
     */
    public static void parseMultipart(Object content) throws MessagingException, IOException {
        if (content instanceof Multipart) {
            Multipart multipart = (Multipart) content;
            int count = multipart.getCount();
            System.out.println("***parseMultipart count =  " + count);
            for (int idx = 0; idx < count; idx++) {
                BodyPart bodyPart = multipart.getBodyPart(idx);
                System.out.println(bodyPart.getContentType());
                System.out.println("***fileName***" + bodyPart.getFileName());
                if (bodyPart.isMimeType("text/plain")) {
                    System.out.println("plain................." + bodyPart.getContent());
                } else if (bodyPart.isMimeType("text/html")) {
                    System.out.println("html..................." + bodyPart.getContent());
                } else if (bodyPart.isMimeType("multipart/*")) {
                    Multipart mpart = (Multipart) bodyPart.getContent();
                    parseMultipart(mpart);
                } else if (bodyPart.isMimeType("application/octet-stream")) {
                    String disposition = bodyPart.getDisposition();
                    System.out.println("***disposition***" + disposition);
                    System.out.println("***disposition***" + bodyPart.getInputStream());
                    if (BodyPart.ATTACHMENT.equalsIgnoreCase(disposition) || bodyPart.getInputStream()!= null) {
                        String fileName = bodyPart.getFileName();
                        System.out.println("****fileName***" + fileName);
                        fileName = MimeUtility.decodeText(fileName);
                        System.out.println("****name2***" + fileName + "***size" + bodyPart.getSize());
                        InputStream is = bodyPart.getInputStream();
                        copy(is, new FileOutputStream("D:\\" + fileName));
                    }
                }
            }
        }

    }

    public static void copy(InputStream is, OutputStream os) throws IOException {
        byte[] bytes = new byte[65536];
        int len;
        while ((len = is.read(bytes)) != -1) {
            os.write(bytes, 0, len);
//            os.write(bytes);
//            os.write(len);
        }
        if (os != null)
            os.close();
        is.close();
    }

    public static void saveParts(Object content, String filename)
            throws IOException, MessagingException
    {
        OutputStream out = null;
        InputStream in = null;
        try {
            if (content instanceof Multipart) {
                Multipart multi = ((Multipart)content);
                int parts = multi.getCount();
                for (int j=0; j < parts; ++j) {
                    MimeBodyPart part = (MimeBodyPart)multi.getBodyPart(j);
                    if (part.getContent() instanceof Multipart) {
                        // part-within-a-part, do some recursion...
                        saveParts(part.getContent(), filename);
                    }
                    else {
                        String extension = "";
                        if (part.isMimeType("text/html")) {
                            extension = "html";
                        }
                        else {
                            if (part.isMimeType("text/plain")) {
                                extension = "txt";
                            }
                            else {
                                //  Try to get the name of the attachment
                                extension = part.getDataHandler().getName();
                            }
                            filename = filename + "." + extension;
                            System.out.println("... " + filename);
                            out = new FileOutputStream(new File(filename));
                            in = part.getInputStream();
                            int k;
                            while ((k = in.read()) != -1) {
                                out.write(k);
                            }
                        }
                    }
                }
            }
        }
        finally {
            if (in != null) { in.close(); }
            if (out != null) { out.flush(); out.close(); }
        }
    }
}
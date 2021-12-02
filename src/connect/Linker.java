package connect;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Linker {
	public static void connect() throws Exception{
		//1.������������
		Socket s = new Socket("127.0.0.1",5612);
		System.out.println("�����ӵ�������5612�˿ڣ�׼������ͼƬ...");
		//��ȡͼƬ�ֽ���
		FileInputStream fis = new FileInputStream("client.bmp");
		//��ȡ�����
		OutputStream out = s.getOutputStream();
		byte[] buf = new byte[1024];
		int len = 0;
		//2.�����������Ͷ������
		while ((len = fis.read(buf)) != -1)
		{
			out.write(buf,0,len);
		}
		//֪ͨ����ˣ����ݷ������
		s.shutdownOutput();
		//3.��ȡ����������ܷ��������͹�������Ϣ���ϴ��ɹ���
		InputStream in = s.getInputStream();
		byte[] bufIn = new byte[1024];
		int num = in.read(bufIn);
		System.out.println(new String(bufIn,0,num));
		//�ر���Դ
		fis.close();
		out.close();
		in.close();
		s.close();
	}
	public static void receive() throws Exception{
		//1.��������ʼ����5612�˿�
		ServerSocket ss = new ServerSocket(5612);
		System.out.println("����������������ڼ���5612�˿�...");
		//�ȴ��ͻ���
		Socket s = ss.accept();
		System.out.println("��⵽�ͻ��ˣ�׼�����ݽ���...");
		//�ͻ��������ӣ���ȡ������
		InputStream in = s.getInputStream();
		//����ͼƬ�ֽ���
		FileOutputStream fos = new FileOutputStream("server.bmp");
		byte[] buf = new byte[1024];
		int len = 0;
		//���ֽ�����дͼƬ����
		while ((len = in.read(buf)) != -1)
		{
			fos.write(buf,0,len);
		}
		//��ȡ�������׼�����ͻ��˷�����Ϣ
		OutputStream out = s.getOutputStream();
		out.write("�ϴ��ɹ�".getBytes());
		//�ر���Դ
		fos.close();
		in.close();
		out.close();
		s.close();
		ss.close();
	}

}

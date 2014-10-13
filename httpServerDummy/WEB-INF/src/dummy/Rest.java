package dummy;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Enumeration;

import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Rest extends HttpServlet {

	private static final long serialVersionUID = 1L;
	// ���X�|���X�t�@�C���p�X�i���[�J�����ɍ��킹�Đݒ�j
	private static final String RESPONSE_FILE_PATH = "C:/test/httpclientTest/response.txt";
	// ���N�G�X�gBODY�T�C�Y�i����Ȃ������瑝�₷�j
	private static final int REQUEST_SIZE = 2048;

	public void doGet(HttpServletRequest request, HttpServletResponse response)
		    throws IOException, ServletException{

		echoStart(request);
		echoHeader(request);
		setResMessage(response);

	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
		    throws IOException, ServletException{

		echoStart(request);
		echoHeader(request);
		echoBody(request);
		setResMessage(response);

	}

	/**
	 * ���X�|���X���b�Z�[�W�쐬
	 * @param response HTTP���X�|���X
	 */
	public void setResMessage(HttpServletResponse response) {

		PrintWriter out = null;
		BufferedReader br = null;
		response.setContentType("text/plain;charset=utf-8");
		try {
			out = response.getWriter();
			br= new BufferedReader(new InputStreamReader(new FileInputStream(RESPONSE_FILE_PATH),"SJIS"));
			String str = "";
			while ((str = br.readLine()) != null) {
				out.println(str);
			}

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (out != null) {
				out.close();
			}
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * �J�n���b�Z�[�W�o��
	 * @param request HTTP���N�G�X�g
	 */
	public void echoStart(HttpServletRequest request) {

		Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy�NMM��dd�� hh:mm:ss");
        String strDate = sdf.format(cal.getTime());
		String path = request.getRequestURI();

		System.out.println("********************** API Start********************");
		System.out.println(strDate + " [" + path + "]");
	}

	/**
	 * HEDER���o��
	 * @param request HTTP���N�G�X�g
	 */
	public void echoHeader(HttpServletRequest request) {

		System.out.println("********************** HEADER **********************");
		Enumeration headernames = request.getHeaderNames();
		while (headernames.hasMoreElements()){
		      String name = (String)headernames.nextElement();
		      Enumeration headervals = request.getHeaders(name);
		      while (headervals.hasMoreElements()){
		        String val = (String)headervals.nextElement();
		        System.out.println(name + ":" + val);
		      }
		}

	}

	/**
	 * BODY���o��
	 * @param request HTTP���N�G�X�g
	 */
	public void echoBody(HttpServletRequest request) {

		System.out.println("********************** BODY   **********************");
		 try {
			ServletInputStream sis = request.getInputStream();
			byte[] bytes = new byte[REQUEST_SIZE];
			sis.read(bytes,0,REQUEST_SIZE);
			System.out.println("UTF-8***********************************************");
			System.out.println(new String(bytes, "UTF-8"));
			System.out.println("SJIS************************************************");
			System.out.println(new String(bytes, "SJIS"));
		} catch (IOException e) {
			// TODO �����������ꂽ catch �u���b�N
			e.printStackTrace();
		}

	}
}

package test.web;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Locale;

import javax.servlet.ServletOutputStream;
import javax.servlet.ServletResponse;

public class TestServletResponse implements ServletResponse {

	@Override
	public String getCharacterEncoding() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getContentType() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ServletOutputStream getOutputStream() throws IOException {
		return new ServletOutputStream() {
			@Override
			public void write(int c) throws IOException {
				System.out.println("#ServletOutputStream.write:"+c+","+(char)c);
			}
		};
	}

	@Override
	public PrintWriter getWriter() throws IOException {
		return new PrintWriter(System.out) {
			@Override
			public void write(String s)  {
				System.out.println("#ServletWriter.write:"+s);
			}
		};
	}

	@Override
	public void setCharacterEncoding(String paramString) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setContentLength(int paramInt) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setContentType(String paramString) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setBufferSize(int paramInt) {
		// TODO Auto-generated method stub

	}

	@Override
	public int getBufferSize() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void flushBuffer() throws IOException {
		// TODO Auto-generated method stub

	}

	@Override
	public void resetBuffer() {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean isCommitted() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void reset() {
		// TODO Auto-generated method stub

	}

	@Override
	public void setLocale(Locale paramLocale) {
		// TODO Auto-generated method stub

	}

	@Override
	public Locale getLocale() {
		// TODO Auto-generated method stub
		return null;
	}

}

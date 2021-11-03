package com.test.structure;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.nio.channels.OverlappingFileLockException;

import com.google.gson.Gson;

public class FileQueueTest {
	public static void main(String[] args) throws Exception {
		//write ������ ����
		long mainFileMaxSize=1024*1024*10;
		String path ="D:\\STORE\\TEST\\";
		String qname ="ATOK_RSLT";
//		Wlock ���� lock
//		 * Header ���� Read Write
//		 * �������� Write
		
		File wLockFile=new File(path+qname+".wlock");
		File headerFile=new File(path+qname+".head");
		//File bodyFile=new File(path+qname+".");
		
		if (wLockFile.exists() == false) {
			wLockFile.createNewFile();
		}
		
		if (headerFile.exists() == false) {
			headerFile.createNewFile();
		}
		
		RandomAccessFile wLockRaf=new RandomAccessFile(wLockFile, "rw");
		
		FileLock wLockFileLock=getFileLock(wLockRaf.getChannel());
		
		
		if(wLockFileLock.isValid() ==false) {
			System.out.println("���϶��� ����� ����");
			while(wLockFileLock.isValid() == false) {
				Thread.sleep(500);
				wLockFileLock=getFileLock(wLockRaf.getChannel());		
			}
			//������ ���� �߰� �ڵ� �ۼ� �ʿ�
		}
					
		RandomAccessFile raf=new RandomAccessFile(headerFile, "rw");
				
		
	}
	
	static public FileLock getFileLock(FileChannel fc) throws IOException {
		FileLock fileLock=null;
		try {
			fileLock=fc.tryLock();
		} catch (OverlappingFileLockException e) {
			fileLock=null;
			e.printStackTrace();
		} catch (ClosedChannelException e) {
			fileLock=null;
			e.printStackTrace();
		} catch (IOException e) {
			fileLock=null;
			e.printStackTrace();
		}
		
		return fileLock; 
	}
	public int write(String qname, Gson gson) {
		
		return 0;
	}

	public int writeFileQueue(String qname, Gson gson) {
		
		return 0;
	}
	
	
	public Gson ReadFileQueue() {
		
		return new Gson();
	}
}
/*
 *����ť ����
 * Wlock ����
 * 	- �������Ͽ� �������� �ش� ���� lock �Ǵ�
 * Rlock ����
 * 	- �������Ͽ��� �б����� �ش� ���� lock �Ǵ�
 * 
 * Header ����(1���� 1 ���� ����)
 *  - �������Ͽ� �����͸� ��Ͽ����� ������ ���/�����Ѵ�.
 *  - �����ε���, �����ε���,������, start offset, end length ����, ���� ���� ���
 * 	- �����ε����� ����ť���� �ε��� ����
 * 	- �����ڴ� ���� ������ �й� ������ �ɼ� ����
 *  - start offset�� ���� ������ length ������ 0
 * 	- ���´� ����, ���(C,R)
 *   
 * Tail ����(1���� 1 ���� ����)
 * 	- �������Ͽ��� �����͸� ��ȸ������ ������ ���/�����Ѵ�.
 *  - �����ε���, �����ε���,������, start offset, end length ����, ���� ���� ���
 *  - �����ε����� ����ť���� �ε��� ����
 * 	- �����ڴ� ���� ������ �й� ������ �ɼ� ����
 *  - start offset�� ���� ������ length ������ 0
 * 	- ���´� ó��, �Ϸ� (R, P, D)
 * 
 * ����ť ������ ����
 * 	- ������ ������ ����Ѵ�. ���� ����� �ʰ��� ���� �����ȴ�
 * 
 * lock ���� �ΰ��� �����Ͽ� �б�� ���⸦ ���ÿ� �ϵ��� �Ѵ�.
 * 	- Wlock ���Ͽ� 1���� �����ڰ� �����Ͽ� ���� ����� 1��������
 * 	- Rlock ���Ͽ� 1���� �����ڰ� �����Ͽ� ���� �б�� 1��������
 *  - �б� ���� ���� ����
 * 
 * ����ť ������
 * Wlock ���� lock
 * Header ���� Read Write
 * �������� Write 
 * 
 * 
 * ����ť���� �����͸� ���� ����
 * Wlock ���� lock���� Ȯ��
 * Wlock ���� lock
 * Header ���� RW���� ����
 * Header task ���� Data line Write
 * ����ť Ÿ�� ���� R���� ����
 * ����ť(Ÿ������)�� �Էµ����� Write��
 * 	- Ÿ�������� ����ť���� �������� �ڵ�������
 *  - Ÿ������ ũ��� ������ ���ϸ� ���� ũ�� �ʰ� �� ����ť��+�ε�����ȣ�� ������
 * Write ���� ���� Ȯ����
 * Header�� task ��������, �ʿ�� �����ε��� ������Ʈ��
 * 
 *  
 *  ����ť ����
 * Rlock ���� lock
 * Header ���� Read
 * Tail ���� Read Write 
 * �������� Read
 * 
 * Rlock ���� Lock ���� Ȯ��
 * Rlock ���� Lock
 * Tail���� RW���� ����
 * Tail������ ������ �ε��� ���� �� ������ ���� ȹ��(READ)
 * 	- ����� ���ٸ� Header ù��° ���� Ȯ��
 *  - ������ ������ ���� ���� ó�� ���� ���� ����- ���̵�� ��üȭ �� ����� �ʿ�
 * Header ���Ͽ��� ��ȸ�� �ε���+1 ���� ȹ��(READ)
 * 	- ��ȸ�� �ε��� ���� �� ���� Ȯ��
 *  - ���°� ��Ⱑ �ɶ����� Wait ��
 * �ش����� ���� ���� ���� ���� ���� ����
 * ���� ���� ó�� �Ϸ� �� Tail���Ͽ� task ������ ����Ѵ�.
 * 
 *  ����ť ������
 *  
 */
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
		//write 생으로 구현
		long mainFileMaxSize=1024*1024*10;
		String path ="D:\\STORE\\TEST\\";
		String qname ="ATOK_RSLT";
//		Wlock 파일 lock
//		 * Header 파일 Read Write
//		 * 본문파일 Write
		
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
			System.out.println("파일락이 잠기지 않음");
			while(wLockFileLock.isValid() == false) {
				Thread.sleep(500);
				wLockFileLock=getFileLock(wLockRaf.getChannel());		
			}
			//구성에 따른 추가 코드 작성 필요
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
 *파일큐 구성
 * Wlock 파일
 * 	- 본문파일에 쓰기전에 해당 파일 lock 건다
 * Rlock 파일
 * 	- 본문파일에서 읽기전에 해당 파일 lock 건다
 * 
 * Header 파일(1라인 1 정보 구성)
 *  - 본문파일에 데이터를 기록에관한 정보를 기록/관리한다.
 *  - 파일인덱스, 라인인덱스,구분자, start offset, end length 정보, 상태 정보 기록
 * 	- 파일인덱스는 파일큐파일 인덱스 정보
 * 	- 구분자는 추후 데이터 분배 기준이 될수 있음
 *  - start offset은 이전 라인의 length 없으면 0
 * 	- 상태는 생성, 대기(C,R)
 *   
 * Tail 파일(1라인 1 정보 구성)
 * 	- 본문파일에서 데이터를 조회에관한 정보를 기록/관리한다.
 *  - 파일인덱스, 라인인덱스,구분자, start offset, end length 정보, 상태 정보 기록
 *  - 파일인덱스는 파일큐파일 인덱스 정보
 * 	- 구분자는 추후 데이터 분배 기준이 될수 있음
 *  - start offset은 이전 라인의 length 없으면 0
 * 	- 상태는 처리, 완료 (R, P, D)
 * 
 * 파일큐 데이터 파일
 * 	- 데이터 정보를 기록한다. 지정 사이즈가 초과시 새로 생성된다
 * 
 * lock 파일 두개를 지정하여 읽기와 쓰기를 동시에 하도록 한다.
 * 	- Wlock 파일에 1개의 수행자가 접근하여 동시 쓰기는 1개만가능
 * 	- Rlock 파일에 1개의 수행자가 접근하여 동시 읽기는 1개만가능
 *  - 읽기 쓰기 동시 가능
 * 
 * 파일큐 라이터
 * Wlock 파일 lock
 * Header 파일 Read Write
 * 본문파일 Write 
 * 
 * 
 * 파일큐에서 데이터를 쓰는 구성
 * Wlock 파일 lock여부 확인
 * Wlock 파일 lock
 * Header 파일 RW모드로 오픈
 * Header task 정보 Data line Write
 * 파일큐 타겟 파일 R모드로 오픈
 * 파일큐(타겟파일)에 입력데이터 Write함
 * 	- 타겟파일은 파일큐명을 기준으로 자동생성됨
 *  - 타겟파일 크기는 설정에 의하면 설정 크기 초과 시 파일큐명+인덱스번호로 생성함
 * Write 정상 수행 확인함
 * Header에 task 상태정보, 필요시 파일인덱스 업데이트함
 * 
 *  
 *  파일큐 리더
 * Rlock 파일 lock
 * Header 파일 Read
 * Tail 파일 Read Write 
 * 본문파일 Read
 * 
 * Rlock 파일 Lock 여부 확인
 * Rlock 파일 Lock
 * Tail파일 RW모드로 오픈
 * Tail파일의 마지막 인덱스 정보 중 대기상태 정보 획득(READ)
 * 	- 기록이 없다면 Header 첫번째 라인 확인
 *  - 구분자 설정에 따른 동시 처리 갯수 증가 가능- 아이디어 구체화 및 고민이 필요
 * Header 파일에서 조회한 인덱스+1 정보 획득(READ)
 * 	- 조회한 인덱스 정보 중 상태 확인
 *  - 상태가 대기가 될때가지 Wait 함
 * 해당정보 기준 으로 본문 파일 내용 읽음
 * 읽은 내용 처리 완료 후 Tail파일에 task 정보를 등록한다.
 * 
 *  파일큐 관리자
 *  
 */
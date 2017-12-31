package edu.buaa.rse.modelCategory;

public class Main {
	public static void main(String[] args){
		String modelFile = null;
		String destinationDirectory = null;
		if(args.length==0){

			System.out.println("usage: the args should be in order like hardwareFile, softwareFile, destinationDirectory");
			
		}
		else if(args.length==2){
			modelFile = args[0];
			destinationDirectory = args[1];
			
		}
		
		
//		ת��������Ҫ���������Ϣ
//			������category
//			ͬһ��category�����˶��ٴ�
//		��hashmap��ʵ��
		InfoExtractor infoExtractor =  new InfoExtractor(modelFile, destinationDirectory);
		infoExtractor.infoExtract();
	}
}

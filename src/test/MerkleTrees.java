package test;

import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.List;

public class MerkleTrees {
	
	List<String> txList;
	
	String root;
	
	public static void main(String[] args) {
		List<String> tempTxList = new ArrayList<String>();
        tempTxList.add("a1");
        tempTxList.add("b2");
        tempTxList.add("c3");
        tempTxList.add("d4");
        tempTxList.add("e5");
        
        MerkleTrees merkleTrees = new MerkleTrees(tempTxList);
        merkleTrees.merkle_tree();
        System.out.println("root : " + merkleTrees.getRoot());
	}
	
	/**
	 * 构造方法
	 * @param txList
	 */
	public MerkleTrees(List<String> txList){
		this.txList = txList;
		root = "";
	}
	
	/**
	 * 生成树并且返回节点
	 */
	public void merkle_tree(){
		
		List<String> tempTxList = new ArrayList<String>();
		
		for(int i=0; i<this.txList.size(); i++){
			tempTxList.add(this.txList.get(i));
		}
		
		List<String> newTxList = getNewTxList(tempTxList);
		
		while (newTxList.size() != 1) {
			newTxList = getNewTxList(newTxList);
		}
		
		this.root = newTxList.get(0);
	}
	
	/**
	 * 返回每个节点的哈希值
	 * @param tempLxList
	 * @return
	 */
	private List<String> getNewTxList(List<String> tempLxList){
		
		List<String> newTxList = new ArrayList<String>();
		
		int index = 0;
		
		while(index < tempLxList.size()){
			//左边
			String left = tempLxList.get(index);
			index++;
			//右边
			String right = "";
			if(index != tempLxList.size()){//如果不是最后一个节点
				right = tempLxList.get(index);
			}
			//计算哈希值
			String sha2HexValue = getSHA2HexValue(left + right);
			newTxList.add(sha2HexValue);
			index++;
		}
		
		return newTxList;		
	}
	
	/**
	 * 返回经过加密的16进制
	 * @param str
	 * @return
	 */
	public String getSHA2HexValue(String str){
		byte[] cipher_byte;
		try{
			MessageDigest md = MessageDigest.getInstance("SHA-256");
			md.update(str.getBytes());
			cipher_byte = md.digest();
			StringBuilder sBuilder = new StringBuilder(2 * cipher_byte.length);
			for(byte b : cipher_byte){
				sBuilder.append(String.format("%02x", b&0xff));
			}
			
			return sBuilder.toString();
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		return "";
	}
	
	
	/**
	 * 返回根节点
	 * @return
	 */
	public String getRoot(){
		return this.root;
	}
}

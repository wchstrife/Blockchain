# 区块链MerkleTree算法实现
## 一、概述
MerkleTree被广泛的应用在比特币技术中，Merkle Tree 是一种数据结构，用于验证在计算机之间和之间存储，处理和传输的任何类型的数据。 目前，Merkle树的主要用途是确保从对等网络中接收的数据块未受损和未改变，和检查其他对等网络没有撒谎发送假数据块。
比特币中每个块中都包含了所有交易的集合签名，这个签名就是用Merkle tree实现的，Merkle树用于比特币以汇总块中的所有事务，产生整个事务集合的整体数字指纹，提供非常有效的过程来验证事务是否包括在块中。 Merkle树一个很重要的用处是检查块中是否包含指定的交易，Merkle树是通过递归哈希节点对来构造的，直到只有一个哈希。 
## 二、Merkle简介
Merkle Tree，是一种树（数据结构中所说的树），网上大都称为Merkle Hash Tree,这是因为 它所构造的Merkle Tree的所有节点都是Hash值。Merkle Tree具有以下特点：
    1. 它是一种树，可以是二叉树，也可以多叉树，无论是几叉树，它都具有树结构的所有特点；
    2. Merkle树的叶子节点上的value，是由你指定的，这主要看你的设计了，如Merkle Hash Tree会将数据的Hash值作为叶子节点的值；
    3 非叶子节点的value是根据它下面所有的叶子节点值，然后按照一定的算法计算而得出的。如Merkle Hash Tree的非叶子节点value的计算方法是将该节点的所有子节点进行组合，然后对组合结果进行hash计算所得出的hash value。
 例如，下图就是一个Merkle Hash Tree形状，如果它是Merkle Hash Tree，则节点7的hash value必须是通过节点15、16上的value计算而得到.
 
![image](http://img.blog.csdn.net/20141106113159703?watermark/2/text/aHR0cDovL2Jsb2cuY3Nkbi5uZXQvZXhwbGVldmU=/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70/gravity/Center)
## 实现过程
这里我们使用一个二叉树来模拟Merkle Tree的操作
过程分为：
- 	准备交易的数据
- 	计算出每个数据的哈希值，从左到右逐步组成树的左右节点
- 	执行循环直到最后一个节点
这里核心的算法就是构造树，并且要计算每个节点的哈希值

```
private List<String> getNewTxList(List<String> tempTxList) {
  List<String> newTxList = new ArrayList<String>();
  int index = 0;
  while (index < tempTxList.size()) {
    // left
    String left = tempTxList.get(index);
    index++;
    // right
    String right = "";
    if (index != tempTxList.size()) {
      right = tempTxList.get(index);
    }
    // sha2 hex value
    String sha2HexValue = getSHA2HexValue(left + right);
    newTxList.add(sha2HexValue);
    index++;
  }

```
测试：
我们构造一个5个叶子节点的二叉树，然后测试返回根节点的哈希值

```

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

```
执行结果
![image](http://img.blog.csdn.net/20170926143124555?watermark/2/text/aHR0cDovL2Jsb2cuY3Nkbi5uZXQvd2Noc3RyaWZl/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70/gravity/SouthEast)
## 参考目录
> http://blog.csdn.net/xiangzhihong8/article/details/53931213
http://blog.csdn.net/expleeve/article/details/40858283
http://www.360doc.com/content/13/0419/22/1073512_279573695.shtml
http://blog.csdn.net/xtu_xiaoxin/article/details/8147956

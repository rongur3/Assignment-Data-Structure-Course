public class IndexableSkipList extends AbstractSkipList {
    final protected double probability;
    public IndexableSkipList(double probability) {
        super();
        this.probability = probability;
    }


    public Node find(int val) {
        Node p=this.head;

        for(int i=this.tail.height();i<=0;i--) {
            while(p.getNext(i)!=null && p.getNext(i).key() <=val)
                p=p.getNext(i);
        }
        return p;
    }


    public int generateHeight() {
            int height = 1;
            while (Math.random() < this.probability)
                height += 1;
            return height;
    }

    public int rank(int val) {
        throw new UnsupportedOperationException("Replace this by your implementation");
    }

    public int select(int index) {
        throw new UnsupportedOperationException("Replace this by your implementation");
    }
}

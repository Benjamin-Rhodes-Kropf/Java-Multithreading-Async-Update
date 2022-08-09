//reads and writes the same data values from the same class from two threads without overlap!
public class MultiThreadSyncData {
    public static void main(String[] args){
        ExampleClass exampleClass = new ExampleClass();
        //both threads are passed the same data to change
        Thread  threadA = new UpdateValueThread(exampleClass, "Thread A");
        Thread  threadB = new ReadValueThread(exampleClass, "Thread B");

        threadA.start();
        threadB.start();
    }

    public static class ExampleClass {
        private double myData = 0; //using a double as an example
        //because its synchronized only one thread can run this code at one time
        //if we remove the fact that this is "synchronized" (you can just delete it it wont break) you can see in the console that data is being skipped
        //the system out must also be in the synchronized loop because it takes some amount of time and in that time data can change
        public synchronized double getMyData(String nameOfThread){
            System.out.println(nameOfThread + " is reading - value is: " + myData);
            return myData;
        }
        public synchronized void setMyData(String nameOfThread, double set){
            myData = set;
            System.out.println(nameOfThread + " is setting - value is: " + myData);
        }
    }


    public static class UpdateValueThread extends Thread{
        protected ExampleClass exampleClass = null;
        private String threadName = null;

        public UpdateValueThread(ExampleClass exampleClass, String nameOfThread){
            this.exampleClass = exampleClass; //passes the class we wish to modify
            this.threadName = nameOfThread; //way to keep track of threads
        }

        public void run() {
            for(int i=0; i<500; i++){
                exampleClass.setMyData( threadName, Math.random());
            }
        }
    }

    public static class ReadValueThread extends Thread{
        protected ExampleClass exampleClass = null;
        private String threadName = null;
        public ReadValueThread(ExampleClass exampleClass, String nameOfThread){
            this.exampleClass = exampleClass;//passes the class we wish to read
            this.threadName = nameOfThread; //way to keep track of threads
        }

        public void run() {
            for(int i=0; i<500; i++){
                exampleClass.getMyData(threadName);
            }
        }
    }
}

import java.util.Iterator;
import java.util.Objects;


/**
 * The concept of the task list does not depend on the task saving method;
 * the users of ArrayTaskList class objects may even be unaware of the way this class is implemented.
 * But the implementation through the array has its drawbacks, such as a slow operation of task deletion.
 * Therefore, for scenarios, in which task deletion often takes place,
 * it is necessary to create the list of tasks that will store tasks in a linked list
 * (single-linked, double-linked or another modification to choose from;
 * one cannot use the already existing implementations, such as java.util.LinkedList and others), which does not have this disadvantage.
 * Create the LinkedTaskList class in the same package with the same methods as ArrayTaskList
 * (in the incoming method change the object type that returns to LinkedTaskList).
 * The objects of this class should behave the same as the objects of ArrayTaskList class.
 * <p>
 * SOURCE : https://www.youtube.com/watch?v=BH6RJf2fVCQ&t=2232s&ab_channel=JavaVision
 */
public class LinkedTaskList<E> extends AbstractTaskList {

    private Node<E> firstNode;
    private Node<E> lastNode;
    // private int size = 0;

    public LinkedTaskList() {
        lastNode = new Node<>(null, firstNode, null);
        firstNode = new Node<>(null, null, lastNode);
    }

    @Override
    public void add(Task task) {
        if (Objects.equals(task, null)) {
            throw new NullPointerException("The task was empty(null)!");
        }
        Node<E> previousElement = lastNode;
        previousElement.setCurrentElement((E) task);
        lastNode = new Node<>(null, previousElement, null);
        previousElement.setNextElement(lastNode);
        size++;
    }


    @Override
    public Task getTask(int index) {

        if (index >= size() || index < 0) {
            throw new IndexOutOfBoundsException("index exceeds the permissible limits for the list!");
        }

        if (index < size() / 2) {
            Node<E> target = firstNode.getNextElement();
            for (int i = 0; i < index; i++) {

                target = target.getNextElement();
            }
            return (Task) target.getCurrentElement();
        } else {
            Node<E> target = lastNode.getPreviousElement();
            for (int i = size(); i > index + 1; i--) {
                target = target.getPreviousElement();
            }
            return (Task) target.getCurrentElement();
        }
    }


    @Override
    public boolean remove(Task task) {
        boolean isSuccess = false;
        Node firstLink = firstNode;

        //int i = 0;
        while (firstLink.getNextElement().getCurrentElement() != null) {
            //  System.out.println("inter: " + i++);
            boolean isRemoved = false;
            Node secondLink = firstLink.getNextElement();
            if (secondLink != null && secondLink.getCurrentElement() != null && secondLink.getCurrentElement().equals(task)) {
                //overwriting the following link
                firstLink.setNextElement(secondLink.getNextElement());
                //overwriting the previous link
                secondLink.getNextElement().setPreviousElement(secondLink.getPreviousElement());
                isRemoved = true;
                isSuccess = true;
                size--;
            }
            if (!isRemoved) {
                firstLink = secondLink;
            }
        }
        return isSuccess;
    }


    @Override
    public Iterator iterator() {
        return new Iterator() {

            private int iteratorCounter = 0;

            @Override
            public boolean hasNext() {
                return iteratorCounter < size();
            }

            @Override
            public Task next() {
                return getTask(iteratorCounter++);
            }
        };
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof LinkedTaskList)) return false;
        LinkedTaskList<?> that = (LinkedTaskList<?>) o;
        return firstNode.equals(that.firstNode) &&
                lastNode.equals(that.lastNode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstNode, lastNode);
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        int j = 0;
        stringBuilder.append("\nThis LinkedTaskList with several Task's: ");
        for (Task value :
                this) {
            stringBuilder.append("\n\n").append(value.toString()).append("\nIndex in LinkedList: ").append(j++);

        }
        return stringBuilder.toString();
    }

    //class of links;
    private class Node<E> {
        private E currentElement;
        private Node<E> nextElement;
        private Node<E> previousElement;

        private Node(E currentElement, Node<E> previousElement, Node<E> nextElement) {
            this.currentElement = currentElement;
            this.previousElement = previousElement;
            this.nextElement = nextElement;

        }

        //getters & setters:
        public E getCurrentElement() {
            return currentElement;
        }

        public void setCurrentElement(E currentElement) {
            this.currentElement = currentElement;
        }

        public Node<E> getNextElement() {
            return nextElement;
        }

        public void setNextElement(Node<E> nextElement) {
            this.nextElement = nextElement;
        }

        public Node<E> getPreviousElement() {
            return previousElement;
        }

        public void setPreviousElement(Node<E> previousElement) {
            this.previousElement = previousElement;
        }


    }

}
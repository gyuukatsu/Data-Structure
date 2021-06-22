import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Genre, Title 을 관리하는 영화 데이터베이스.
 * 
 * MyLinkedList 를 사용해 각각 Genre와 Title에 따라 내부적으로 정렬된 상태를  
 * 유지하는 데이터베이스이다. 
 */
public class MovieDB {
	MyLinkedList<MyLinkedList<MovieDBItem>> database;
    public MovieDB() {
        // FIXME implement this
    	database = new MyLinkedList<MyLinkedList<MovieDBItem>>();
    	// HINT: MovieDBGenre 클래스를 정렬된 상태로 유지하기 위한 
    	// MyLinkedList 타입의 멤버 변수를 초기화 한다.
    }

    public void insert(MovieDBItem item) {
        // FIXME implement this
        // Insert the given item to the MovieDB.
    	Iterator<MyLinkedList<MovieDBItem>> itr = database.iterator();
    	
    	if(database.isEmpty()) {
    		MyLinkedList<MovieDBItem> new_genrelist = new MyLinkedList<MovieDBItem>();
    		new_genrelist.add(item);
    		MovieDBItem dummy_item = new MovieDBItem(item.getGenre(),"dummy_genre");
    		new_genrelist.head.setItem(dummy_item);

    		database.add(new_genrelist);
    	}
    	else {
    		int piv=0, cnt=0, itr_piv=0;
    		
    		while(itr.hasNext() && cnt==0) {
    			MyLinkedList<MovieDBItem> itr_list = itr.next();
    			MovieDBItem itr_genre = itr_list.first();

    			if(itr_genre.getGenre().equals(item.getGenre())){
    				Iterator<MovieDBItem> movie_itr= itr_list.iterator();
    				while(movie_itr.hasNext()) {
    					MovieDBItem movie = movie_itr.next();
    					piv++;
    					if(movie.compareTo(item)==0) {
    						cnt++;
    						break;
    					}
    					else if(movie.compareTo(item)>0) {
    						itr_list.add(piv,item);
    						cnt++;
    						break;
    					}
    				}
    				if(cnt==0) {
    					itr_list.add(item);
    					cnt++;
    				}
    			}
    			else if(itr_genre.compareTo(item)==2) {
    				break;
    			}
    			itr_piv++;
    		}
    		
    		if(cnt==0) {
    			MyLinkedList<MovieDBItem> new_genrelist = new MyLinkedList<MovieDBItem>();
        		new_genrelist.add(item);
        		MovieDBItem dummy_item = new MovieDBItem(item.getGenre(),"dummy_genre");
        		new_genrelist.head.setItem(dummy_item);
        		database.add(itr_piv+1, new_genrelist);
    		}
    	}
    	// Printing functionality is provided for the sake of debugging.
        // This code should be removed before submitting your work.
        //System.err.printf("[trace] MovieDB: INSERT [%s] [%s]\n", item.getGenre(), item.getTitle());
    }

    public void delete(MovieDBItem item) {
        // FIXME implement this
        // Remove the given item from the MovieDB.
    	Iterator<MyLinkedList<MovieDBItem>> itr = database.iterator();
    	
    	int find=0;
    	while(itr.hasNext() && find==0) {
    		MyLinkedList<MovieDBItem> itr_list = itr.next();
			MovieDBItem itr_genre = itr_list.first();

			if(itr_genre.getGenre().equals(item.getGenre())){
				Iterator<MovieDBItem> movie_itr = itr_list.iterator();
				while(movie_itr.hasNext()) {
					MovieDBItem movie =  movie_itr.next();
					if(movie.compareTo(item)==0) {
						find++;
						movie_itr.remove();
						if(itr_list.size()==0) {
							itr.remove();
						}
						break;
					}
				}
			}
    	}
    	// Printing functionality is provided for the sake of debugging.
        // This code should be removed before submitting your work.
        //System.err.printf("[trace] MovieDB: DELETE [%s] [%s]\n", item.getGenre(), item.getTitle());
    }

    public MyLinkedList<MovieDBItem> search(String term) {
        // FIXME implement this
        // Search the given term from the MovieDB.
        // You should return a linked list of MovieDBItem.
        // The search command is handled at SearchCmd class.
    	
    	// Printing search results is the responsibility of SearchCmd class. 
    	// So you must not use System.out in this method to achieve specs of the assignment.
    	
        // This tracing functionality is provided for the sake of debugging.
        // This code should be removed before submitting your work.
    	//System.err.printf("[trace] MovieDB: SEARCH [%s]\n", term);
    	
    	// FIXME remove this code and return an appropriate MyLinkedList<MovieDBItem> instance.
    	// This code is supplied for avoiding compilation error.   
        MyLinkedList<MovieDBItem> results = new MyLinkedList<MovieDBItem>();
        Iterator<MyLinkedList<MovieDBItem>> itr = database.iterator();
        
        while(itr.hasNext()) {
        	MyLinkedList<MovieDBItem> itr_list = itr.next();
			
			Iterator<MovieDBItem> movie_itr = itr_list.iterator();
			while(movie_itr.hasNext()) {
				MovieDBItem movie =  movie_itr.next();
				if(movie.getTitle().contains(term) == true) {
					results.add(movie);
				}
			}
        }
        
        return results;
    }
    
    public MyLinkedList<MovieDBItem> items() {
        // FIXME implement this
        // Search the given term from the MovieDatabase.
        // You should return a linked list of QueryResult.
        // The print command is handled at PrintCmd class.

    	// Printing movie items is the responsibility of PrintCmd class. 
    	// So you must not use System.out in this method to achieve specs of the assignment.

    	// Printing functionality is provided for the sake of debugging.
        // This code should be removed before submitting your work.
        //System.err.printf("[trace] MovieDB: ITEMS\n");

    	// FIXME remove this code and return an appropriate MyLinkedList<MovieDBItem> instance.
    	// This code is supplied for avoiding compilation error.   
        MyLinkedList<MovieDBItem> results = new MyLinkedList<MovieDBItem>();
        Iterator<MyLinkedList<MovieDBItem>> itr= database.iterator();
        Iterator<MovieDBItem> itr_movie;
        MovieDBItem movie;
        
        while(itr.hasNext()) {
        	MyLinkedList<MovieDBItem> itr_list = itr.next();
        	itr_movie=itr_list.iterator();
        	while(itr_movie.hasNext()) {
        		movie=itr_movie.next();
        		results.add(movie);
        	}
        }
    	return results;
    }
}
/*
class Genre extends Node<String> implements Comparable<Genre> {
	public Genre(String name) {
		super(name);
		throw new UnsupportedOperationException("not implemented yet");
	}
	
	@Override
	public int compareTo(Genre o) {
		throw new UnsupportedOperationException("not implemented yet");
	}

	@Override
	public int hashCode() {
		throw new UnsupportedOperationException("not implemented yet");
	}

	@Override
	public boolean equals(Object obj) {
		throw new UnsupportedOperationException("not implemented yet");
	}
}

class MovieList implements ListInterface<String> {	
	Node<String> head;
	int numItems;
	
	public MovieList() {
		head = new Node<String>(null);
		numItems=0;
	}

	@Override
	public Iterator<String> iterator() {
		return new MovieListIterator<String>(this);
		//throw new UnsupportedOperationException("not implemented yet");
	}

	@Override
	public boolean isEmpty() {
		return head.getNext() == null;
		//throw new UnsupportedOperationException("not implemented yet");
	}

	@Override
	public int size() {
		return numItems;
		//throw new UnsupportedOperationException("not implemented yet");
	}

	@Override
	public void add(String item) {
		Node<String> last = head;
		while (last.getNext() != null) {
			last = last.getNext();
		}
		last.insertNext(item);
		numItems += 1;
		//throw new UnsupportedOperationException("not implemented yet");
	}

	@Override
	public String first() {
		return head.getNext().getItem();
		//throw new UnsupportedOperationException("not implemented yet");
	}

	@Override
	public void removeAll() {
		head.setNext(null);
		//throw new UnsupportedOperationException("not implemented yet");
	}
}

class MovieListIterator<T> implements Iterator<T> {
	// FIXME implement this
	// Implement the iterator for MyLinkedList.
	// You have to maintain the current position of the iterator.
	private MovieList list;
	private Node<String> curr;
	private Node<String> prev;

	public MovieListIterator(MovieList list) {
		this.list = list;
		this.curr = list.head;
		this.prev = null;
	}

	@Override
	public boolean hasNext() {
		return curr.getNext() != null;
	}

	@Override
	public T next() {
		if (!hasNext())
			throw new NoSuchElementException();

		prev = curr;
		curr = curr.getNext();
		System.out.println("1");

		return (T) curr.getItem();
	}

	@Override
	public void remove() {
		if (prev == null)
			throw new IllegalStateException("next() should be called first");
		if (curr == null)
			throw new NoSuchElementException();
		prev.removeNext();
		list.numItems -= 1;
		curr = prev;
		prev = null;
	}
}*/
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

public class Movie {

private final int movieId;
private final float rating;
private List<Movie> similarMovies;

public Movie(int movieId,float rating) {
// TODO Auto-generated constructor stub

this.movieId = movieId;
this.rating = rating;
similarMovies = new ArrayList<Movie>();

}

public List<Movie> getSimilarMovies() {
return similarMovies;
}

public void addSimilarMovie(Movie movie) {
similarMovies.add(movie);

}

public void setSimilarMovies(List<Movie> similarMovies) {
this.similarMovies = similarMovies;
}

public int getMovieId() {
return movieId;
}

public float getRating() {
return rating;
}


public static List<Movie> getMovieRecommendations(Movie movie, int numTopRatedSimilarMovies) {
TreeSet<Movie> container = new TreeSet<Movie>(new Comparator<Movie>() {
@Override
public int compare(Movie m1, Movie m2) {
return new Float(m1.getRating()).compareTo(new Float(m2.getRating()));
}
});
List<Movie> result = new ArrayList<Movie>();
// Visit every node and add it to collection
traverseMovieNetwork(movie, movie.getSimilarMovies(), container);
// Get required number of movies from collection
for (; numTopRatedSimilarMovies > 0 && container.size() > 0; numTopRatedSimilarMovies--) {
result.add(container.pollLast());
}
return result;
}

/**
* Traverse the graph
*/
public static void traverseMovieNetwork(Movie movie, List<Movie> similarMovies, Set<Movie> store) {
for (Movie m : similarMovies) {
if (!movie.equals(m) && !store.contains(m)) {
store.add(m);
traverseMovieNetwork(movie, m.getSimilarMovies(), store);
}
}
}

/**
* Test
*/
public static void main(String[] args) {
Movie movieA = new Movie(1, 1.2f);
Movie movieB = new Movie(2, 2.4f);
Movie movieC = new Movie(3, 3.6f);
Movie movieD = new Movie(4, 4.8f);

movieA.addSimilarMovie(movieB);
movieA.addSimilarMovie(movieC);
movieB.addSimilarMovie(movieA);
movieB.addSimilarMovie(movieD);
movieC.addSimilarMovie(movieA);
movieC.addSimilarMovie(movieD);
movieD.addSimilarMovie(movieB);
movieD.addSimilarMovie(movieC);

for (Movie m : getMovieRecommendations(movieA, 2)) {
System.out.println("Movie id=" + m.getMovieId() + " Rating=" + m.getRating());
}

System.out.println("-------------------------------------------------");

for (Movie m : getMovieRecommendations(movieA, 4)) {
System.out.println("Movie id=" + m.getMovieId() + " Rating=" + m.getRating());
}

System.out.println("-------------------------------------------------");

for (Movie m : getMovieRecommendations(movieA, 1)) {
System.out.println("Movie id=" + m.getMovieId() + " Rating=" + m.getRating());
}
}
}


class SortMap implements Comparator<Movie> {

Map<Movie, Float> mov;
public SortMap(Map<Movie, Float> mov) {
// TODO Auto-generated constructor stub

this.mov = mov;
}
@Override
public int compare(Movie o1, Movie o2) {
// TODO Auto-generated method stub

System.out.print(mov.get(o1));
return mov.get(o1)>mov.get(o2)?1:-1;
}


}

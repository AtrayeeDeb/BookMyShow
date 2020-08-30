package com.example.bookmyshow.models;
import com.fasterxml.jackson.annotation.JsonIgnore;
import javax.persistence.*;
import java.util.*;

@Entity
public class MovieShow {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String movieName;
    private int screenNumber;
    private String theatreName;
    private String theatreAdress;
    private String date;
    private String time;
    private int rows;
    private int cols;
    private int filled =0;

    @ManyToOne
    @JsonIgnore
    Movie movie;

    @ManyToOne
    @JsonIgnore
    Theatre theatre;

    @OneToMany(mappedBy = "movieShow", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<MovieTicket> movieTickets;

    @OneToMany(mappedBy = "movieShow", cascade = CascadeType.ALL)
    @JsonIgnore
    public List<Seat> seats;

    public MovieShow() {
        super();
    }

    public MovieShow(String movieName, int screenNumber, String theatreName, String theatreAdress,
                     String date, String time, int rows, int cols, Movie movie, Theatre theatre) {
        super();
        this.movieName = movieName;
        this.screenNumber = screenNumber;
        this.theatreName = theatreName;
        this.theatreAdress = theatreAdress;
        this.date = date;
        this.time = time;
        this.rows = rows;
        this.cols = cols;
        setMovie(movie);
        setTheatre(theatre);
        //createSeats(rows, cols);
    }

//    private void createSeats(int rows, int cols) {
//        seats = new Seat[rows][cols];
//        for(int i=0;i<rows;i++){
//            for(int j=0;j<cols;j++){
//                if(i<3){
//                    seats[i][j]= new Seat(i, j, 120, true);//different prices according to seats
//                }
//                else if(i>=rows-2){
//                    seats[i][j]= new Seat(i, j, 180, true);
//                }
//                else{
//                    seats[i][j]= new Seat(i, j, 150, true);
//                }
//            }
//        }
//    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMovieName() {
        return movieName;
    }

    public void setMovieName(String movieName) {
        this.movieName = movieName;
    }

    public int getScreenNumber() {
        return screenNumber;
    }

    public void setScreenNumber(int screenNumber) {
        this.screenNumber = screenNumber;
    }

    public String getTheatreName() {
        return theatreName;
    }

    public void setTheatreName(String theatreName) {
        this.theatreName = theatreName;
    }

    public String getTheatreAdress() {
        return theatreAdress;
    }

    public void setTheatreAdress(String theatreAdress) {
        this.theatreAdress = theatreAdress;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Movie getMovie() {
        return movie;
    }

    public void setMovieTickets(List<MovieTicket> movieTickets) {
        this.movieTickets = movieTickets;
    }

    public List<Seat> getSeats() {
        return seats;
    }

    public void setSeats(List<Seat> seats) {
        this.seats = seats;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
        if (!movie.getMovieShows().contains(this)) {
            movie.getMovieShows().add(this);
        }
    }

    public Theatre getTheatre() {
        return theatre;
    }

    public void setTheatre(Theatre theatre) {
        this.theatre = theatre;
        if (!theatre.getMovieShows().contains(this)) {
            theatre.getMovieShows().add(this);
        }
    }



    public List<MovieTicket> getMovieTickets() {
        return movieTickets;
    }

    public void setMovieTicket(MovieTicket movieTicket) {
        this.movieTickets.add(movieTicket);
        if (movieTicket.getMovieShow() != this) {
            movieTicket.setMovieShow(this);
        }
    }
    public void set(MovieShow newMovieShow) {
        if(newMovieShow.getMovieName()!=null){
            setMovieName(newMovieShow.getMovieName());
        }
        if(newMovieShow.getScreenNumber()!=this.getScreenNumber()){
            setScreenNumber(newMovieShow.getScreenNumber());
        }
        if(newMovieShow.getTheatreName()!=null){
            setTheatreName(newMovieShow.getTheatreName());
        }
        if(newMovieShow.getTheatreAdress()!=null){
            setTheatreAdress(newMovieShow.getTheatreAdress());
        }
        if(newMovieShow.getDate()!=null){
            setDate(newMovieShow.getDate());
        }
        if(newMovieShow.getTime()!=null){
            setTime(newMovieShow.getTime());
        }
    }

    public int getRows() {
        return rows;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }

    public int getCols() {
        return cols;
    }

    public void setCols(int cols) {
        this.cols = cols;
    }
    public int getFilled() {
        return filled;
    }

    public void setFilled(int filled) {
        this.filled = filled;
    }
}
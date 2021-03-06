package com.c09.cinpockema.product.controller;

import javax.validation.Valid;
import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.c09.cinpockema.user.service.SessionService;
import com.c09.cinpockema.product.service.ProductService;
import com.c09.cinpockema.cinema.service.CinemaService;
import com.c09.cinpockema.movie.service.MovieService;
import com.c09.cinpockema.user.entities.User;
import com.c09.cinpockema.user.entities.User.ROLE;
import com.c09.cinpockema.cinema.entities.Cinema;
import com.c09.cinpockema.cinema.entities.Hall;
import com.c09.cinpockema.movie.entities.Movie;
import com.c09.cinpockema.cinema.entities.Seat;
import com.c09.cinpockema.product.entities.Screening;
import com.c09.cinpockema.product.entities.Ticket;

@RestController
@RequestMapping("/product")
public class ProductController {

	@Autowired
	private ProductService productService;
	
	@Autowired
	private CinemaService cinemaService;
	
	@Autowired
	private MovieService movieService;
	
	// curl localhost:8080/api/product/screenings/1
	@RequestMapping(value={"/screenings/{cinemaId}"}, method=RequestMethod.GET)
	public List<Screening> listScreeningsByCinameId(@PathVariable("cinemaId") long cinemaId) {
		return productService.listScreeningsByCinameId(cinemaId);
	}
	
	// curl localhost:8080/api/product/screenings/1/1 -u admin:admin -H "Content-Type: application/json" -d "{\"startTime\":\"11:30\", \"runningTime\":120}"
	@RequestMapping(value = {"/screenings/{hallId}/{movieId}"}, method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.CREATED)
    @PreAuthorize("hasAuthority('admin')")
    public Screening createScreening(@Valid @RequestBody Screening screening, @PathVariable("hallId") long hallId, @PathVariable("movieId") long movieId) {
		Hall hall = cinemaService.getHallById(hallId);
		Movie movie = movieService.getMovieById(movieId);
		return productService.createScreening(screening, hall, movie);
	}
	
	// curl -X DELETE localhost:8080/api/product/screenings/1 -u admin:admin
	@RequestMapping(value={"/screenings/{screeningId}"}, method=RequestMethod.DELETE)
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    @PreAuthorize("hasAuthority('admin')")
    public void deleteScreeningById(@PathVariable("screeningId") long screeningId) {
		productService.deleteScreeningById(screeningId);
	}
	
	// curl localhost:8080/api/product/tickets/1
	@RequestMapping(value={"/tickets/{id}"}, method=RequestMethod.GET)
	public ResponseEntity<Ticket> getTicketById(@PathVariable("id") long ticketId) {
		Ticket ticket = productService.getTicketById(ticketId);
		return new ResponseEntity<Ticket>(ticket, ticket != null ? HttpStatus.OK : HttpStatus.NOT_FOUND);
	}
	
	// curl localhost:8080/api/product/tickets/1/1 -u admin:admin -H "Content-Type: application/json" -d "{\"price\":80}"
	@RequestMapping(value = {"/tickets/{screeningId}/{seatId}"}, method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.CREATED)
    @PreAuthorize("hasAuthority('admin')")
    public Ticket createTicket(@Valid @RequestBody Ticket ticket, @PathVariable("screeningId") long screeningId, @PathVariable("seatId") long seatId) {
		Screening screening = productService.getScreeningById(screeningId);
		Seat seat = cinemaService.getSeatById(seatId);
		return productService.createTicket(ticket, screening, seat);
	}
	
	// curl -X DELETE localhost:8080/api/product/tickets/1 -u admin:admin
	@RequestMapping(value={"/tickets/{id}"}, method=RequestMethod.DELETE)
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    @PreAuthorize("hasAuthority('admin')")
    public void deleteTicketById(@PathVariable("id") long id) {
		productService.deleteTicketById(id);
	}
}

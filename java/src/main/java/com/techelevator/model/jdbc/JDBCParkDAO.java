package com.techelevator.model.jdbc;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import com.techelevator.model.Park;
import com.techelevator.model.ParkDAO;

public class JDBCParkDAO implements ParkDAO {

	private JdbcTemplate jdbcTemplate;
	
	public JDBCParkDAO(DataSource ds) {
		this.jdbcTemplate = new JdbcTemplate(ds);
	}
	
	@Override
	public List<Park> getAllParks() {
		List<Park> parks = new ArrayList <Park>();
		String allParks = "SELECT * FROM park";
		SqlRowSet results = jdbcTemplate.queryForRowSet(allParks);
		while (results.next()) {
			Park thisPark = mapRowToPark(results);
			parks.add(thisPark);
		}
		return parks;
	}

	public List<String> getParkNames() {
		List<Park> parks = getAllParks();
		List<String> names = new ArrayList <String>();
		for (Park i : parks) {
			names.add(i.getName());
		}
		names.add("Exit");
		return names;
	}
	
	public void desplayParkInfo(long parkId) {
		List<Park> parks = getAllParks();
		for (Park i : parks) {
			if (i.getParkId() == parkId) {
				System.out.println("ID\t\t" + i.getParkId());
				System.out.println("Name\t\t" + i.getName());
				System.out.println("Location\t" + i.getLocation());
				System.out.println("Area\t\t" + i.getArea());
				System.out.println("Annual visitors\t" + i.getVisitors());
				String [] tempArray = i.getDescription().split(" ");
				for (int j = 0; j < tempArray.length; j++) {
					System.out.print(tempArray[j] + " ");
					if (j % 10 == 0 && j != 0) {
						System.out.println();
					}
				}
				System.out.println();
			}
		}
	}
	
	private Park mapRowToPark(SqlRowSet results) {
		Park thisPark = new Park();
		thisPark.setParkId(results.getLong("park_id"));
		thisPark.setName(results.getString("name"));
		thisPark.setLocation(results.getString("location"));
		thisPark.setEstablishDate(LocalDate.parse(results.getString("establish_date")));
		thisPark.setArea(results.getInt("area"));
		thisPark.setVisitors(results.getInt("visitors"));
		thisPark.setDescription(results.getString("Description"));
		return thisPark;
	}
	
}

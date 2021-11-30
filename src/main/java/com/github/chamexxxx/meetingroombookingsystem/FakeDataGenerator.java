package com.github.chamexxxx.meetingroombookingsystem;

import com.github.chamexxxx.meetingroombookingsystem.dto.AccountDto;
import com.github.chamexxxx.meetingroombookingsystem.dto.MeetDto;
import com.github.chamexxxx.meetingroombookingsystem.dto.ParticipantDto;
import com.github.chamexxxx.meetingroombookingsystem.utils.DateUtils;
import com.github.javafaker.Faker;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.Date;
import java.util.stream.IntStream;

public class FakeDataGenerator {
    private final Faker faker = new Faker();

    public void generate() throws SQLException {
        var accountDto = createAccount();
        createMeets(accountDto);
    }

    private AccountDto createAccount() throws SQLException {
        var username = faker.name().username();
        var password = faker.internet().password(10, 16, true, true);

        var accountDto = new AccountDto(username, password);

        Database.getAccountDao().create(accountDto);

        System.out.println("Created account with username " + username + " and password " + password);

        return accountDto;
    }

    private void createMeets(AccountDto accountDto) {
        IntStream.range(0, 1).forEachOrdered(value -> {
            var room = "Room " + faker.random().nextInt(1, 10);
            var dates = getRandomDates();
            var startDate = new Timestamp(dates[0].getTime());
            var endDate = new Timestamp(dates[1].getTime());

            var meetDto = new MeetDto(room, startDate, endDate, accountDto);

            try {
                Database.getMeetDao().create(meetDto);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
    }

    private ArrayList<ParticipantDto> generateParticipants() {
        var participants = new ArrayList<ParticipantDto>();

        IntStream.range(0, 5).forEachOrdered(value -> {
            participants.add(new ParticipantDto(faker.name().fullName()));
        });

        return participants;
    }

    private Date[] getRandomDates() {
        var startDate = getRandomDateOfCurrentMonth();
        var endDate = DateUtils.addHoursToDate(startDate, faker.random().nextInt(1, 6));

        return new Date[]{startDate, endDate};
    }

    private Date getRandomDateOfCurrentMonth() {
        var yearMonth = YearMonth.now();
        var firstOfMonth = yearMonth.atDay(1);
        var lastOfMonth = yearMonth.atEndOfMonth();

        return faker.date().between(DateUtils.asDate(firstOfMonth), DateUtils.asDate(lastOfMonth));
    }
}

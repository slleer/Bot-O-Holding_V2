package com.botofholding.api.ServiceTests.BohUserTests;

import com.botofholding.api.Domain.Entity.BohUser;
import com.botofholding.api.Service.Interfaces.BohUserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test") // [FIX] Activate the 'test' profile to disable data initializers.
@AutoConfigureTestDatabase // [FIX] Ensure this test uses the in-memory H2 database.
public class BohUserDTOTests {

    @Autowired
    private BohUserService userService;

    // Helper method to create a common test user
//    private BohUser createAndSaveTestUser(String userName, Long discordId) {
//        BohUser user = new BohUser();
//        user.setBohUserName(userName);
//        user.setBohUserDiscordId(discordId);
//        return userService.createBohUser(user);
//    }

    private void deleteCreatedUsers(BohUser userToDelete) {
        userService.deleteBohUser(userToDelete.getId());
    }

    @Test
    void contextLoads() {
        assertThat(userService).isNotNull();
    }
}

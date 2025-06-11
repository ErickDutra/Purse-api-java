package purse.coin.purse.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import purse.coin.purse.dto.LoginDto;
import purse.coin.purse.dto.UsersDto;
import purse.coin.purse.service.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;



@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/user")
public class UserController {

    UserService userService;
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<UsersDto> cadastrar(@RequestBody UsersDto usersDto) {
    UsersDto novoUsuario = userService.cadastrarUser(usersDto);
    if (novoUsuario == null) {
        return ResponseEntity.status(HttpStatus.CONFLICT).build(); 
    }
    return ResponseEntity.ok(novoUsuario);
    }


    @PostMapping("/login")
    public ResponseEntity<UsersDto> login(@RequestBody LoginDto loginDto) {
        UsersDto user = userService.login(loginDto);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build(); 
        }
        return ResponseEntity.ok(user);
    }

    @PutMapping("/update")
    public ResponseEntity<UsersDto> updateUser(@RequestBody UsersDto usersDto) {
        UsersDto updatedUser = userService.updateUser(usersDto);
        if (updatedUser == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build(); 
        }
        return ResponseEntity.ok(updatedUser);
    }

    @DeleteMapping("/delete/{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable String userId) {
        boolean deleted = userService.deleteUser(userId);
        if (!deleted) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build(); 
        }
        return ResponseEntity.noContent().build();
    }


    @GetMapping("/getAll")
    public ResponseEntity<List<UsersDto>> getUser() {
        List<UsersDto> users = userService.getAllUsers();
        if (users.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build(); 
        }
        return ResponseEntity.ok(users);
        
    }
}

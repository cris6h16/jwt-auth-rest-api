package org.cris6h16.Repositories;

import org.cris6h16.Models.UserModel;

import java.util.List;
import java.util.Optional;

// port
public interface UserRepository {
    void saveCustom(UserModel userModel);

    boolean existsByUsernameCustom(String username);

    boolean existsByEmailCustom(String email);

    boolean existsByIdCustom(Long id);

    void updateEmailVerifiedByIdCustom(Long id, boolean isVerified);

    Optional<UserModel> findByEmailCustom(String email);

    Optional<UserModel> findByIdCustom(Long id);

    void deactivate(Long id);

    void updateUsernameByIdCustom(Long id, String newUsername);

    Optional<String> findPasswordByIdCustom(Long id);

    void updatePasswordByIdCustom(Long id, String newPassword);

    List<UserModel> findAllCustom(int page, int pageSize, String sortBy, String sortDirection);
}

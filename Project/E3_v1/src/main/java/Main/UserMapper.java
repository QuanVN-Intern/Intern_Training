/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Main;
import org.apache.ibatis.annotations.Select;
import models.User;
/**
 *
 * @author quant
 */
public interface UserMapper {
    @Select("SELECT * FROM users WHERE id = #{id}")
    User getUserById(int id);
}

package model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.*;

import javax.persistence.*;
import javax.validation.constraints.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Confectionery
{
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="id")
    private Integer id;

    @NotEmpty(message="Изделие должно иметь название")
    @Size(min=2, max=20, message="Название изделия должно быть от 2 до 20 символов")
    private String name;

    @NotEmpty(message="Изделие должно иметь описание")
    @Size(min=10, message="Описание изделия должно быть не менее 10 символов")
    private String description;

    @NotNull(message="Заполните поле 'калорийность'")
    private Double calories;

    @NotNull(message="Заполните поле 'содержание белков'")
    private Double protein;

    @NotNull(message="Заполните поле 'содержание жиров'")
    private Double fat;

    @NotNull(message="Заполните поле 'содержание углеводов'")
    private Double carbohydrates;

    @NotNull(message="Заполните поле 'вес'")
    @DecimalMin(value="0.01", message = "Вес должен быть больше 0")
    private Double weight;

    @NotNull(message="Заполните поле 'цена'")
    @DecimalMin(value="0.01", message = "Цена должна быть больше 0")
    private Double price;

    private int sum = 0;
    private int k = 0;
}

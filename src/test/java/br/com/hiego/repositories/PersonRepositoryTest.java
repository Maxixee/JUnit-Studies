package br.com.hiego.repositories;

import br.com.hiego.model.Person;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class PersonRepositoryTest {

    @Autowired
    private PersonRepository repository;

    private Person person;

    @BeforeEach
    public void setup(){
        person = new Person("Hiego",
                "Barreto",
                "hiego@email.com",
                "Test street",
                "Male");
    }

    @DisplayName("Given Person Object when Save then Return Saved Person")
    @Test
    void testGivenPersonObject_whenSave_thenReturnSavedPerson(){
        //Arrange

        //when
        Person savedPerson = repository.save(person);

        //assert
        assertEquals(person, savedPerson);
        assertNotNull(savedPerson);
        assertTrue(savedPerson.getId() > 0);
    }

    @DisplayName("Given Person List when Find All then Return Person List")
    @Test
    void testGivenPersonList_whenFindAll_thenReturnPersonList(){
        //Arrange
        Person person1 = new Person("Anakin",
                "Skywalker",
                "anakin@email.com",
                "Death Star",
                "Male");

        repository.save(person);
        repository.save(person1);

        //when
        List<Person> personList = repository.findAll();

        //assert
        assertNotNull(personList);
        assertEquals(personList.size(), 2);
    }

    @DisplayName("Given Person Object when Find By Id then Return Person Object")
    @Test
    void testGivenPersonObject_whenFindById_thenReturnPersonObject(){
        //Arrange
        repository.save(person);

        //when
        Person searchPerson = repository.findById(person.getId()).get();

        //assert
        assertNotNull(searchPerson);
        assertEquals(searchPerson.getId(), person.getId());
    }

    @DisplayName("Given Person Object when Find By Email then Return Person Object")
    @Test
    void testGivenPersonObject_whenFindByEmail_thenReturnPersonObject(){
        //Arrange
        repository.save(person);

        //when
        Person searchPerson = repository.findByEmail(person.getEmail()).get();

        //assert
        assertNotNull(searchPerson);
        assertEquals(searchPerson.getEmail(), person.getEmail());
    }

    @DisplayName("Given Person Object when Upadate Person then Return Updated Person Object")
    @Test
    void testGivenPersonObject_whenUpdatePerson_thenReturnUpdatedPersonObject(){
        //Arrange
        repository.save(person);

        //when
        Person savedPerson = repository.findById(person.getId()).get();
        savedPerson.setFirstName("Ligma");
        savedPerson.setEmail("hiego@email.com");

        Person updatedPerson = repository.save(savedPerson);

        //assert
        assertNotNull(updatedPerson);
        assertEquals(updatedPerson.getFirstName(), "Ligma");
        assertEquals(updatedPerson.getEmail(), "hiego@email.com");
    }

    @DisplayName("Given Person Object when Delete then Remove Person Object")
    @Test
    void testGivenPersonObject_whenDelete_thenRemovePersonObject(){
        //Arrange
        repository.save(person);

        //when
        repository.deleteById(person.getId());
        Optional<Person> personOptional = repository.findById(person.getId());

        //assert
        assertTrue(personOptional.isEmpty());
    }

    @DisplayName("Given First Name And Last Name when Find By JPQL then Return Person Object")
    @Test
    void testGivenFirstNameAndLastName_whenFindByJPQL_thenReturnPersonObject(){
        //Arrange
        repository.save(person);

        //when
        Person savedPerson = repository.findByJPQL("Hiego", "Barreto");

        //assert
        assertNotNull(savedPerson);
        assertEquals(savedPerson.getFirstName(), "Hiego");
        assertEquals(savedPerson.getLastName(), "Barreto");
    }

    @DisplayName("Given First Name And Last Name when Find By JPQL Named Parameters then Return Person Object")
    @Test
    void testGivenFirstNameAndLastName_whenFindByJPQLNamedParameters_thenReturnPersonObject(){
        //Arrange
        repository.save(person);

        //when
        Person savedPerson = repository.findByJPQLNamedParameters("Hiego", "Barreto");

        //assert
        assertNotNull(savedPerson);
        assertEquals(savedPerson.getFirstName(), "Hiego");
        assertEquals(savedPerson.getLastName(), "Barreto");
    }

    @DisplayName("Given First Name And Last Name when Find By Native SQL then Return Person Object")
    @Test
    void testGivenFirstNameAndLastName_whenFindByNativeSQL_thenReturnPersonObject(){
        //Arrange
        repository.save(person);

        //when
        Person savedPerson = repository.findByNativeSQL("Hiego", "Barreto");

        //assert
        assertNotNull(savedPerson);
        assertEquals(savedPerson.getFirstName(), "Hiego");
        assertEquals(savedPerson.getLastName(), "Barreto");
    }

    @DisplayName("Given First Name And Last Name when Find By Native SQL With Named Parameters then Return Person Object")
    @Test
    void testGivenFirstNameAndLastName_whenFindByNativeSQLWithNamedParameters_thenReturnPersonObject(){
        //Arrange
        repository.save(person);

        //when
        Person savedPerson = repository.findByNativeSQLwithNamedParameters("Hiego", "Barreto");

        //assert
        assertNotNull(savedPerson);
        assertEquals(savedPerson.getFirstName(), "Hiego");
        assertEquals(savedPerson.getLastName(), "Barreto");
    }

}
package ephyl.util.validator;

import ephyl.util.ConnectionManager;
import ephyl.dao.StudentDaoJdbc;
import ephyl.dto.StudentDto;
import ephyl.model.Student;
import ephyl.util.exception.NotUniqueNameException;

public class StudentDtoValidator {
    private StudentDaoJdbc studentDaoJdbc = new StudentDaoJdbc();

    public StudentDtoValidator(StudentDaoJdbc studentDaoJdbc) {
        this.studentDaoJdbc = studentDaoJdbc;
    }

    public StudentDtoValidator() {
    }

    public boolean validate(StudentDto studentDto) throws NotUniqueNameException {
        if (!isAgeValid(studentDto) || !isGenderValid(studentDto)) return false;
        if (studentDto.getId() != 0) {
            if (!isIdExists(studentDto)) return false;
        }
        return isNameUnique(studentDto);
    }

    private boolean isAgeValid(StudentDto studentDto) {
        return (studentDto.getAge() <= 120) && (studentDto.getAge() > 10);
    }

    private static boolean isGenderValid(StudentDto studentDto) {
        return studentDto.getGender() != null;
    }

    private boolean isIdExists(StudentDto studentDto) {
        if (studentDto.getId() < 0) return false;
        Student student = new Student();
        student.setId(studentDto.getId());
        return studentDaoJdbc.isIdInBase(student);
    }


    private boolean isNameUnique(StudentDto studentDto) throws NotUniqueNameException {
        if (studentDto.getName() == null || studentDto.getName().length() <= 1) return false;
        Student student = new Student();
        student.setId(studentDto.getId());
        student.setName(studentDto.getName());
        if (!studentDaoJdbc.isStudentNewNameUnique(student)) throw new NotUniqueNameException();
        return true;
    }
}

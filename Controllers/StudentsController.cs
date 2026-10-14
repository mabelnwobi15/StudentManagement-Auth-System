using Microsoft.AspNetCore.Mvc;
using System.Collections.Generic;

namespace StudentManagement.Controllers
{
    [Route("api/students")]
    [ApiController]
    public class StudentsController : ControllerBase
    {
        [HttpGet]
        public IActionResult GetStudents()
        {
            // Sample student data to send back to the Android app's Admin panel
            var students = new List<object>
            {
                new { id = "1", name = "John Doe", email = "john@school.com", course = "Computer Science", gpa = 3.8 },
                new { id = "2", name = "Jane Smith", email = "jane@school.com", course = "Information Technology", gpa = 3.5 }
            };

            return Ok(students);
        }
    }
}
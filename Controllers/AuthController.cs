using Microsoft.AspNetCore.Mvc;

namespace StudentManagement.Controllers
{

    [Route("api/auth")]
    [ApiController]
    public class AuthController : ControllerBase
    {
        [HttpPost("login")]
        public IActionResult Login([FromBody] LoginRequest request)
        {
            // Simple hardcoded check for demonstration (replace with database lookup)
            if (request.Email == "admin@school.com" && request.Password == "password123")
            {
                return Ok(new { token = "fake-jwt-token-12345", role = "ADMIN", message = "Login successful" });
            }
            else if (request.Email == "student@school.com" && request.Password == "password123")
            {
                return Ok(new { token = "fake-jwt-token-67890", role = "STUDENT", message = "Login successful" });
            }

            return Unauthorized(new { message = "Invalid email or password" });
        }
    }

    public class LoginRequest
    {
        public string Email { get; set; }
        public string Password { get; set; }
    }
}


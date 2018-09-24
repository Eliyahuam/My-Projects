using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.ComponentModel.DataAnnotations;
using System.Linq;
using System.Web;

namespace Shauli_Blog.Models
{
    public class Fans
    {
        public int ID { get; set; }
        [Required]
        public string Name { get; set; }
        [DisplayName("Last Name")]
        [Required]
        public string LastName { get; set; }
        [Required]
        [DisplayName("Gender")]
        public string MaleFemale { get; set; }
        [Required]
        [DisplayName("BirthDay")]
        [DataType(DataType.Date)]
        public DateTime BDate { get; set; }
        [Required]
        [DisplayName("Time in Club")]
        public string TimeInClub { get; set; }
    }
}
using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.ComponentModel.DataAnnotations;
using System.Linq;
using System.Web;

namespace Shauli_Blog.Models
{
    public class Post
    {
       

        public Post ()
        {
            PostDate = DateTime.Now;
        }
        public int ID { get; set; }
        [Required]
        [DisplayName("Post Title")]
        public string Title { get; set; }
        [Required]
        public string Author { get; set; }
        [DisplayName("Author Site")]
        [Url]
        public string SiteOfAuthor { get; set; }

        [DisplayName("Posted at")]
        public DateTime PostDate { get; set; }
        [Required]
        public string Content { get; set; }

        public virtual List<Comment> Comments { get; set; }
    }
}
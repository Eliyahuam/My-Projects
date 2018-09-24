using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Linq;
using System.Web;

namespace Shauli_Blog.Models
{
    public class Comment
    {

        public int ID { get; set; }
        public int PostID { get; set; }
        [DisplayName("Comment Title")]
        public string Title { get; set; }
        public string AuthorName { get; set; }
        [DisplayName("Author Site")]
        public string SiteOfAuthor { get; set; }
        public string Content { get; set; }
        public virtual Post Post { get; set; }
    }
}
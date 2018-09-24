using System;
using System.Collections.Generic;
using System.Data.Entity;
using System.Linq;
using System.Web;

namespace Shauli_Blog.Models
{
    public class dbFan : DbContext
    {

            public DbSet<Fans> fans { get; set; }
            public DbSet<Post> Posts { get; set; }
            public DbSet<Comment> Comments { get; set; }

    }
}
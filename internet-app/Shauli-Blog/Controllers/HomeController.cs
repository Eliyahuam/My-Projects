using Shauli_Blog.Models;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Mvc;

namespace Shauli_Blog.Controllers
{
    public class HomeController : Controller
    {
        private dbFan db = new dbFan();
        // GET: Home
        public ActionResult Index()
        {
            return View(db.Posts.ToList());
        }


        public ActionResult postComment(int postid, string title, string authorname, string authorsite, string content)
        {
            var comment = new Comment()
            {
                PostID = postid,
                Title = title,
                AuthorName = authorname,
                SiteOfAuthor = authorsite,
                Content = content
            };

            db.Comments.Add(comment);
            db.SaveChanges();

            return RedirectToAction("Index");
        }
    }


}
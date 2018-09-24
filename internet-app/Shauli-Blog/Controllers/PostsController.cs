using System;
using System.Collections.Generic;
using System.Data;
using System.Data.Entity;
using System.Linq;
using System.Net;
using System.Web;
using System.Web.Mvc;
using Shauli_Blog.Models;

namespace Shauli_Blog.Controllers
{
    public class PostsController : Controller
    {
        private dbFan db = new dbFan();

        // GET: Posts
        public ActionResult Index()
        {
            return View(db.Posts.ToList());
        }

        // GET: Posts/Details/5
        public ActionResult Details(int? id)
        {
            if (id == null)
            {
                return new HttpStatusCodeResult(HttpStatusCode.BadRequest);
            }
            Post post = db.Posts.Find(id);
            if (post == null)
            {
                return HttpNotFound();
            }
            return View(post);
        }

        // GET: Posts/Create
        public ActionResult Create()
        {
            return View();
        }

        // POST: Posts/Create
        // To protect from overposting attacks, please enable the specific properties you want to bind to, for 
        // more details see http://go.microsoft.com/fwlink/?LinkId=317598.
        [HttpPost]
        [ValidateAntiForgeryToken]
        public ActionResult Create([Bind(Include = "ID,Title,Author,SiteOfAuthor,PostDate,Content")] Post post, HttpPostedFileBase photo, HttpPostedFileBase video)
        {
            if (ModelState.IsValid)
            {
                db.Posts.Add(post);
                db.SaveChanges();
                if (photo != null && photo.ContentLength > 0)
                { photo.SaveAs(Server.MapPath("~/upFiles/" + post.ID + ".png")); }
                if (video != null && video.ContentLength > 0)
                { video.SaveAs(Server.MapPath("~/upFiles/" + post.ID + ".mp4")); }
                return RedirectToAction("Index");
            }

            return View(post);
        }

        // GET: Posts/Edit/5
        public ActionResult Edit(int? id)
        {
            if (id == null)
            {
                return new HttpStatusCodeResult(HttpStatusCode.BadRequest);
            }
            Post post = db.Posts.Find(id);
            if (post == null)
            {
                return HttpNotFound();
            }
            return View(post);
        }

        // POST: Posts/Edit/5
        // To protect from overposting attacks, please enable the specific properties you want to bind to, for 
        // more details see http://go.microsoft.com/fwlink/?LinkId=317598.
        [HttpPost]
        [ValidateAntiForgeryToken]
        public ActionResult Edit([Bind(Include = "ID,Title,Author,SiteOfAuthor,PostDate,Content")] Post post, HttpPostedFileBase photo, HttpPostedFileBase video)
        {
            if (ModelState.IsValid)
            {
                db.Entry(post).State = EntityState.Modified;
                db.SaveChanges();
                
                if (photo != null && photo.ContentLength > 0)
                {
                    var photoName = "";
                    photoName = post.ID + ".png";
                    string path = Request.MapPath("~/upFiles/" + photoName);
                    if (System.IO.File.Exists(path))
                    {
                        System.IO.File.Delete(path);
                    }
                    
                    photo.SaveAs(Server.MapPath("~/upFiles/" + post.ID + ".png"));
                }
                if (video != null && video.ContentLength > 0)
                {
                    var videoName = "";
                    videoName = post.ID + ".mp4";
                    string path = Request.MapPath("~/upFiles/" + videoName);
                    if (System.IO.File.Exists(path))
                    {
                        System.IO.File.Delete(path);
                    }
                    video.SaveAs(Server.MapPath("~/upFiles/" + post.ID + ".mp4"));
                }
                return RedirectToAction("Index");
            }
            return View(post);
        }

        // GET: Posts/Delete/5
        public ActionResult Delete(int? id)
        {
            if (id == null)
            {
                return new HttpStatusCodeResult(HttpStatusCode.BadRequest);
            }
            Post post = db.Posts.Find(id);
            if (post == null)
            {
                return HttpNotFound();
            }
            return View(post);
        }

        // POST: Posts/Delete/5
        [HttpPost, ActionName("Delete")]
        [ValidateAntiForgeryToken]
        public ActionResult DeleteConfirmed(int id)
        {
            Post post = db.Posts.Find(id);
            db.Posts.Remove(post);
            db.SaveChanges();

            var photoName = "";
            photoName = post.ID + ".png";
            string photopath = Request.MapPath("~/upFiles/" + photoName);
            if (System.IO.File.Exists(photopath))
            {
                System.IO.File.Delete(photopath);
            }
            var videoName = "";
            videoName = post.ID + ".mp4";
            string videopath = Request.MapPath("~/upFiles/" + videoName);
            if (System.IO.File.Exists(videopath))
            {
                System.IO.File.Delete(videopath);
            }

            return RedirectToAction("Index");
        }

        protected override void Dispose(bool disposing)
        {
            if (disposing)
            {
                db.Dispose();
            }
            base.Dispose(disposing);
        }
    }
}

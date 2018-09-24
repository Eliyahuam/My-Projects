namespace Shauli_Blog.Migrations
{
    using System;
    using System.Data.Entity.Migrations;
    
    public partial class dbFans : DbMigration
    {
        public override void Up()
        {
            AlterColumn("dbo.Posts", "Title", c => c.String(nullable: false));
            AlterColumn("dbo.Posts", "Author", c => c.String(nullable: false));
            AlterColumn("dbo.Posts", "Content", c => c.String(nullable: false));
        }
        
        public override void Down()
        {
            AlterColumn("dbo.Posts", "Content", c => c.String());
            AlterColumn("dbo.Posts", "Author", c => c.String());
            AlterColumn("dbo.Posts", "Title", c => c.String());
        }
    }
}

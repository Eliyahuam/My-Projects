namespace Shauli_Blog.Migrations
{
    using System;
    using System.Data.Entity.Migrations;
    
    public partial class dbFan : DbMigration
    {
        public override void Up()
        {
            AlterColumn("dbo.Fans", "Name", c => c.String(nullable: false));
            AlterColumn("dbo.Fans", "LastName", c => c.String(nullable: false));
            AlterColumn("dbo.Fans", "MaleFemale", c => c.String(nullable: false));
            AlterColumn("dbo.Fans", "BDate", c => c.DateTime(nullable: false));
            AlterColumn("dbo.Fans", "TimeInClub", c => c.String(nullable: false));
        }
        
        public override void Down()
        {
            AlterColumn("dbo.Fans", "TimeInClub", c => c.String());
            AlterColumn("dbo.Fans", "BDate", c => c.String());
            AlterColumn("dbo.Fans", "MaleFemale", c => c.String());
            AlterColumn("dbo.Fans", "LastName", c => c.String());
            AlterColumn("dbo.Fans", "Name", c => c.String());
        }
    }
}

Seam Build System
------------------

This readme describes the build and dependency management system used to build 
Seam, it's examples, and seam-gen.  If you are looking for information on 
building or configuring dependencies for a project which uses Seam, you should
look at the chapter on dependencies and building in the reference manual.

Eclipse Classpath
-----------------

If you want to generate an eclipse classpath based on the maven repository, run
ant eclipseclasspath in the root folder. To download sources for Seam's 
dependencies to your local m2 repository, run ant downloadDependenciesSources;
the eclipseclasspath task automatically picks these up if they are available and
attaches them to library in eclipse.

Dependency Management
---------------------

The dependency managmement for Seam is managed through Maven pom files.  The
pom's are located in the build/ directory.  The root.pom.xml is the 'root' or
parent pom for Seam, and contains versioning information for all dependencies.
Each Seam jar has it's own pom (e.g. pdf.pom.xml) which declares the dependencies
for that module - it has the root pom declared as it's parent; no version 
information is placed in this pom.

Seam directly uses the 'compile' dependencies to build the various modules,
and the test scope (for core) to run core tests.

To add or upgrade a dependency of Seam:
---------------------------------------

* Find the dependency in a maven repository - check repository.jboss.org/maven2
  first and then try mvnsearch.com.

* Add or update the entry in root.pom.xml including version information

* If it's a new dependency, add an entry to the correct module.  If it's an
  optional dependency (most are), mark it <optional>true</optional>.  If it's
  provided by JBoss AS (current targeted version), mark it <scope>provided</scope>

* Bear in mind that a released Seam shouldn't depend on a SNAPSHOT version

* When we release Seam we have to add all it's dependencies to 
  repository.jboss.org (no thirdparty repositories should be used for released
  versions) - so if you are adding a dependency which is stable, and you aren't
  planning to change the dependency before the next release you should add it
  straight to repository.jboss.org.  To do this:
  1) Checkout repository.jboss.org/maven2 from svn (https://svn.jboss.org/repos/repository.jboss.org)
  2) Set the offline.repository.jboss.org property in build/build.properties to
     the directory you checked out to.
  3) Run ant -Dpom=foo.pom -Djar=foo.jar deployRelease
     - if the new dependency also has a source and javadoc jar you can run:
       - "ant -Dpom=foo.pom -Djar=foo.jar -Dsrcjar=foo-src.jar -Ddocjar=foo-doc.jar deployReleaseWithSourcesAndJavaDoc"
  4) Check in the changed files to SVN (they'll be under a path of 
     artifactId/groupId/version)

  
To add a unreleased dependency of Seam:
-----------------------------------------

* If you need a dependency which isn't available in Maven, and don't want to add
  it straight to repository.jboss.org or want to depend on a CVS/snapshot of a 
  project which you're planning to upgrade before the next Seam release you
  can add it to snapshots.jboss.org.
  
* To add a jar to the local repository, you can, if you have a pom (that you
  copied from an earlier version or have written) run:

  ant deploySnapshot -Dpom=foo.pom -Djar=foo.jar
  
  If you want maven to create a basic pom for you:
  
  ant deploySnapshot -Djar=foo.jar
  
  You will be prompted for your jboss.org username and pasword (WARNING your
  password is echoed back to you!)
  
* If you need to alter the pom or jar in a repository but don't change
  the version number, you should delete the old copy from maven's cache
  
  rm -rf ~/.m2/repository/group/id/artifactId/version
  

Release Instructions
--------------------

All dependencies for a released version of Seam should be available in 
repository.jboss.org.  Only released versions of software should be present in
repository.jboss.org.

Release dependencies:

* Check that all dependencies of Seam are present in repository.jboss.org
  - Check that snapshots.jboss.org is not active
  - Check that no other maven repositorys are enabled
* Follow the proceedure outlined above to add jars to repository.jboss.org
  
Add Seam to repository.jboss.org:

* Checkout repository.jboss.org/maven2 from svn (https://svn.jboss.org/repos/repository.jboss.org)
* Set the offline.repository.jboss.org property in build/build.properties to the
  directory you checked out to.
* Run ant releaseSeam
* Commit the release to repository.jboss.org


Examples
--------

The examples assemble all the Seam dependencies into a staging directory (/lib).
/lib/*.jar is used as the classpath to compile the examples, and the examples
use pattern's to select the jars to put in their deployed archives.

Some trickery (excluding jars) is required to get JBoss Embedded to run 
currently - this should be improved.
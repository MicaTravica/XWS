mvn install:install-file -Dfile=exist-modules.jar -DgroupId=exist \
    -DartifactId=exist-modules -Dversion=1.1.1 -Dpackaging=jar \
    -DgeneratePom=true

mvn install:install-file -Dfile=exist.jar -DgroupId=exist \
    -DartifactId=exist-db -Dversion=1.1.1 -Dpackaging=jar \
    -DgeneratePom=true

mvn install:install-file -Dfile=j8fu-1.21.jar -DgroupId=j8fu \
    -DartifactId=j8fu -Dversion=1.21 -Dpackaging=jar \
    -DgeneratePom=true

mvn install:install-file -Dfile=pkg-java-fork.jar -DgroupId=pkg-java-fork \
    -DartifactId=pkg-java-fork -Dversion=1.1 -Dpackaging=jar \
    -DgeneratePom=true



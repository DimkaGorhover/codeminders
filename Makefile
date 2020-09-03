cpu_count=4
ram=512m

__GRADLE_IMAGE_TAG=gradle:6.6.1-jdk8

__JAVA_OPTS=\
	-server \
	-XX:+UseG1GC \
	-XX:+UseContainerSupport \
	-Dfile.encoding=UTF-8

__GRADLE_OPTS=\
	--no-daemon \
	--parallel \
	--max-workers $(cpu_count)

__BUILD_CMD=gradle $(__GRADLE_OPTS) clean shadowJar

__RUN_CMD=java $(__JAVA_OPTS) \
	-jar /project_dir/build/libs/app.jar \
	-d /project_dir/src \
	-p 2 \
	-e build,target,bin,out,resources

docker=docker run -i -t \
	--rm \
	--cpus $(cpu_count).00 \
	--name gradle-codeminders \
	--memory $(ram) \
	--memory-reservation $(ram) \
	--memory-swap 0 \
	--user 1000 \
	--env GRADLE_OPTS='$(__JAVA_OPTS)' \
	-v gradle-cache:/home/gradle/.gradle \
	-v m2-cache:/home/gradle/.m2 \
	-v $(shell pwd):/project_dir \
	-w /project_dir \
	$(__GRADLE_IMAGE_TAG)

gradle=$(docker) gradle $(__GRADLE_OPTS)

init:
	$(gradle) init

wrapper:
	$(gradle) wrapper

clean:
	$(gradle) clean

compile:
	$(gradle) clean testClasses

test:
	$(gradle) clean test

run:
	$(docker) sh -c '$(__BUILD_CMD) && $(__RUN_CMD) '

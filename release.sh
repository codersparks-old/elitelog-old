# Script that will perform a release of elite log

if [[ $# != 2 ]]
then
	echo "Usage $0 [elitelog base folder] [openshift base folder]"
	exit 1
fi

ELITELOG_BASE_DIR=$1
OPENSHIFT_BASE_DIR=$2
TAG=$(date +%Y%m%d%H%M%S)

echo "Using elitelog dir: ......... $ELITELOG_BASE_DIR"
echo "Using elitelog openshift dir: $OPENSHIFT_BASE_DIR"
echo "Using tag: .................. $TAG"

echo -e "\nPreparing elite log..."

pushd $ELITELOG_BASE_DIR

if [[ $(git status | grep "nothing to commit, working directory clean" | wc -l) -eq "1" ]]
then 
	echo "No uncommited changes...continuing"
else 
	echo "Uncommitted changes...cannot continue"
	exit 2
fi

echo -e "\nBuilding elite log..."

mvn clean package

echo -e "\nTagging repository with tag 'elitelog-$TAG'"
git tag -a "elitelog-$TAG" -m "Adding tag: elitelog-$TAG"

popd

echo -e "Copying war to $OPENSHIFT_BASE_DIR/webapps/ROOT.war"
cp $ELITELOG_BASE_DIR/target/eliitelog.war $OPENSHIFT_BASE_DIR

pipeline {
    agent any

    triggers {
        pollSCM('*/5 * * * *')
    }

    stages {
        stage('Compile') {
            steps {
                custExecGradleTask('clean')
            }
        }
        stage('Unit Tests') {
            steps {
                custExecGradleTask('test')
            }
            post {
                always {
                    junit '**/build/test-results/test/TEST-*.xml'
                }
            }
        }
        stage('Long-running Verification') {
            environment {
                SONAR_LOGIN = credentials('SONARCLOUD_TOKEN')
            }
            parallel {
                stage('Integration Tests') {
                    steps {
                        custExecGradleTask('integrationTest')
                    }
                    post {
                        always {
                            junit '**/build/test-results/integrationTest/TEST-*.xml'
                        }
                    }
                }
                stage('Code Analysis') {
                    steps {
                        custExecGradleTask('sonarqube')
                    }
                }
            }
        }
        stage('Assemble') {
            steps {
                custExecGradleTask('assemble')
                stash includes: '**/build/libs/*.war', name: 'app'
            }
        }
        stage('Promotion') {
            steps {
                timeout(time: 1, unit:'DAYS') {
                    input 'Deploy to Production?'
                }
            }
        }
        stage('Deploy to Production') {
            environment {
                HEROKU_API_KEY = credentials('HEROKU_API_KEY')
            }
            steps {
                unstash 'app'
                custExecGradleTask('deployHeroku')
            }
        }
    }

    post {
        always {
            echo 'I have finished'
			//mail to: 'billa.sreenivasarao@gmail.com', subject: 'Build Completed', body: 'Please verify status!'
            deleteDir() // clean up workspace
        }
        success {
            echo 'I succeeded!'
        }
        unstable {
            echo 'I am unstable :/'
        }
        failure {
            echo 'I failed :('
        }
        changed {
            echo 'Things are different...'
        }
    }
	
}

def custExecGradleTask(String... args) {
    sh "./gradlew ${args.join(' ')} -s"
}
before_script:
  - echo "CI Build Start"

after_script:
  - echo "CI Build Finish"

stages:
  - build
  - deploy
  - rollback


integration-api-build:
  stage: build
  script:
    - sh ${r'${DEPLOYMENT_SHELL_PATH}'} --stage build --type api --environment integration --project ${r'${CI_PROJECT_NAME}'}
  only:
    - integration
  tags:
    - shell

integration-api-deploy:
  stage: deploy
  script:
    - sh ${r'${DEPLOYMENT_SHELL_PATH}'} --stage deploy --type api --environment integration --project ${r'${CI_PROJECT_NAME}'}
  only:
    - integration
  tags:
    - shell

integration-api-rollback:
  stage: rollback
  script:
    - sh ${r'${DEPLOYMENT_SHELL_PATH}'} --stage rollback --type api --environment integration --project ${r'${CI_PROJECT_NAME}'}
  only:
    - integration
  tags:
    - shell
  when: manual


test6-api-build:
  stage: build
  script:
    - sh ${r'${DEPLOYMENT_SHELL_PATH}'} --stage build --type api --environment test --project ${r'${CI_PROJECT_NAME}'} --shadow 6
  only:
    - test6
  tags:
    - shell

test6-api-deploy:
  stage: deploy
  script:
    - sh ${r'${DEPLOYMENT_SHELL_PATH}'} --stage deploy --type api --environment test --project ${r'${CI_PROJECT_NAME}'} --shadow 6
  only:
    - test6
  tags:
    - shell

test6-api-rollback:
  stage: rollback
  script:
    - sh ${r'${DEPLOYMENT_SHELL_PATH}'} --stage rollback --type api --environment test --project ${r'${CI_PROJECT_NAME}'} --shadow 6
  only:
    - test6
  tags:
    - shell
  when: manual


test5-api-build:
  stage: build
  script:
    - sh ${r'${DEPLOYMENT_SHELL_PATH}'} --stage build --type api --environment test --project ${r'${CI_PROJECT_NAME}'} --shadow 5
  only:
    - test5
  tags:
    - shell

test5-api-deploy:
  stage: deploy
  script:
    - sh ${r'${DEPLOYMENT_SHELL_PATH}'} --stage deploy --type api --environment test --project ${r'${CI_PROJECT_NAME}'} --shadow 5
  only:
    - test5
  tags:
    - shell

test5-api-rollback:
  stage: rollback
  script:
    - sh ${r'${DEPLOYMENT_SHELL_PATH}'} --stage rollback --type api --environment test --project ${r'${CI_PROJECT_NAME}'} --shadow 5
  only:
    - test5
  tags:
    - shell
  when: manual


test4-api-build:
  stage: build
  script:
    - sh ${r'${DEPLOYMENT_SHELL_PATH}'} --stage build --type api --environment test --project ${r'${CI_PROJECT_NAME}'} --shadow 4
  only:
    - test4
  tags:
    - shell

test4-api-deploy:
  stage: deploy
  script:
    - sh ${r'${DEPLOYMENT_SHELL_PATH}'} --stage deploy --type api --environment test --project ${r'${CI_PROJECT_NAME}'} --shadow 4
  only:
    - test4
  tags:
    - shell

test4-api-rollback:
  stage: rollback
  script:
    - sh ${r'${DEPLOYMENT_SHELL_PATH}'} --stage rollback --type api --environment test --project ${r'${CI_PROJECT_NAME}'} --shadow 4
  only:
    - test4
  tags:
    - shell
  when: manual


test3-api-build:
  stage: build
  script:
    - sh ${r'${DEPLOYMENT_SHELL_PATH}'} --stage build --type api --environment test --project ${r'${CI_PROJECT_NAME}'} --shadow 3
  only:
    - test3
  tags:
    - shell

test3-api-deploy:
  stage: deploy
  script:
    - sh ${r'${DEPLOYMENT_SHELL_PATH}'} --stage deploy --type api --environment test --project ${r'${CI_PROJECT_NAME}'} --shadow 3
  only:
    - test3
  tags:
    - shell

test3-api-rollback:
  stage: rollback
  script:
    - sh ${r'${DEPLOYMENT_SHELL_PATH}'} --stage rollback --type api --environment test --project ${r'${CI_PROJECT_NAME}'} --shadow 3
  only:
    - test3
  tags:
    - shell
  when: manual


test2-api-build:
  stage: build
  script:
    - sh ${r'${DEPLOYMENT_SHELL_PATH}'} --stage build --type api --environment test --project ${r'${CI_PROJECT_NAME}'} --shadow 2
  only:
    - test2
  tags:
    - shell

test2-api-deploy:
  stage: deploy
  script:
    - sh ${r'${DEPLOYMENT_SHELL_PATH}'} --stage deploy --type api --environment test --project ${r'${CI_PROJECT_NAME}'} --shadow 2
  only:
    - test2
  tags:
    - shell

test2-api-rollback:
  stage: rollback
  script:
    - sh ${r'${DEPLOYMENT_SHELL_PATH}'} --stage rollback --type api --environment test --project ${r'${CI_PROJECT_NAME}'} --shadow 2
  only:
    - test2
  tags:
    - shell
  when: manual


test-api-build:
  stage: build
  script:
    - sh ${r'${DEPLOYMENT_SHELL_PATH}'} --stage build --type api --environment test --code swt14 --project ${r'${CI_PROJECT_NAME}'}
  only:
    - test
  tags:
    - shell

test-api-deploy:
  stage: deploy
  script:
    - sh ${r'${DEPLOYMENT_SHELL_PATH}'} --stage deploy --type api --environment test --code swt14 --project ${r'${CI_PROJECT_NAME}'}
  only:
    - test
  tags:
    - shell

test-api-rollback:
  stage: rollback
  script:
    - sh ${r'${DEPLOYMENT_SHELL_PATH}'} --stage rollback --type api --environment test --code swt14 --project ${r'${CI_PROJECT_NAME}'}
  only:
    - test
  tags:
    - shell
  when: manual


pre-api-build:
  stage: build
  script:
    - sh ${r'${DEPLOYMENT_SHELL_PATH}'} --stage build --type api --environment pre --code swt14 --project ${r'${CI_PROJECT_NAME}'}
  only:
    - pre
  tags:
    - shell

pre-api-deploy:
  stage: deploy
  script:
    - sh ${r'${DEPLOYMENT_SHELL_PATH}'} --stage deploy --type api --environment pre --code swt14 --project ${r'${CI_PROJECT_NAME}'}
  only:
    - pre
  tags:
    - shell

pre-api-rollback:
  stage: rollback
  script:
    - sh ${r'${DEPLOYMENT_SHELL_PATH}'} --stage rollback --type api --environment pre --code swt14 --project ${r'${CI_PROJECT_NAME}'}
  only:
    - pre
  tags:
    - shell
  when: manual


prod-api-build:
  stage: build
  script:
    - sh ${r'${DEPLOYMENT_SHELL_PATH}'} --stage build --type api --environment prod --code swt14 --project ${r'${CI_PROJECT_NAME}'}
  only:
    - prod
  tags:
    - shell

prod-api-deploy:
  stage: deploy
  script:
    - sh ${r'${DEPLOYMENT_SHELL_PATH}'} --stage deploy --type api --environment prod --code swt14 --project ${r'${CI_PROJECT_NAME}'}
  only:
    - prod
  tags:
    - shell
  when: manual

prod-api-rollback:
  stage: rollback
  script:
    - sh ${r'${DEPLOYMENT_SHELL_PATH}'} --stage rollback --type api --environment prod --code swt14 --project ${r'${CI_PROJECT_NAME}'}
  only:
    - prod
  tags:
    - shell
  when: manual

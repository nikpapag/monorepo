# Issue Resolution: Java Build Failures

## Problem
Java Maven builds were failing with:
```
[ERROR] The goal you specified requires a project to execute but there is no POM 
in this directory (/harness/monorepo-foo/app2-java-teamB). Please verify you 
invoked Maven from the correct directory.
```

## Root Cause
The `.gitignore` files had an overly broad pattern:
```gitignore
*.xml   # ❌ This ignored EVERYTHING including pom.xml!
```

This caused `pom.xml` files (required for Maven builds) to never be committed to the repository.

## Solution
Fixed `.gitignore` to be specific about which XML files to ignore:

### Before (❌ Wrong):
```gitignore
# Test and Coverage
coverage.xml
test-results.xml
*.xml   # Too broad!
```

### After (✅ Correct):
```gitignore
# Test and Coverage (be specific, don't ignore pom.xml!)
coverage.xml
test-results.xml
lcov.info
**/target/surefire-reports/*.xml
**/target/site/jacoco/*.xml
htmlcov/
```

## Changes Made
1. ✅ Updated `monorepo-foo/.gitignore`
2. ✅ Updated `monorepo-bar/.gitignore`  
3. ✅ Force-added all `pom.xml` files:
   - `monorepo-foo/app2-java-teamB/pom.xml`
   - `monorepo-foo/app3-java-teamB/pom.xml`
   - `monorepo-bar/app6-java-teamB/pom.xml`
   - `monorepo-bar/app7-java-teamA/pom.xml`

## What We're Ignoring Now
✅ **Test/Coverage Reports** (generated files):
- `coverage.xml` - Coverage reports
- `test-results.xml` - JUnit test results
- `**/target/surefire-reports/*.xml` - Maven test reports
- `**/target/site/jacoco/*.xml` - JaCoCo coverage reports

✅ **NOT Ignoring** (required files):
- `pom.xml` - Maven project descriptor (REQUIRED!)
- `application.properties` - Spring configuration
- Other configuration XMLs

## Testing
After pushing this fix, Maven builds should:
1. ✅ Find the pom.xml file
2. ✅ Successfully compile
3. ✅ Run tests
4. ✅ Generate coverage reports

## Lesson Learned
**Never use blanket patterns like `*.xml` in .gitignore!**

Instead, be specific about what you want to ignore:
- ✅ `test-results.xml` (specific file)
- ✅ `**/target/**/*.xml` (specific directory pattern)
- ❌ `*.xml` (too broad, breaks builds)

## Related Files
- [JAVA_TEST_STEP.yaml](./JAVA_TEST_STEP.yaml) - Template for Java test steps
- [PYTHON_TEST_STEP.yaml](./PYTHON_TEST_STEP.yaml) - Template for Python test steps

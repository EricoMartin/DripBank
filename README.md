# DripBank
A mobile fintech solution

## Contibuting to this project

Fork and clone this repo if you have not done so.
Commit messages must adhere to the conventional commits spec defined here https://www.conventionalcommits.org/en/v1.0.0-beta.4/.

## <a name="coc"></a> Code of Conduct

Please read and follow our [code of conduct](https://github.com//EricoMartin/DripBank/tree/master/CODE_OF_CONDUCT.md)


### Before working on a new feature:
- If your clone is not new, sync your local develop branch with the upstream develop. [How-To](https://help.github.com/en/github/collaborating-with-issues-and-pull-requests/syncing-a-fork)
- Create a new branch for the feature you are working on
- Branch  naming  convention  must follow [Branch Naming](https://namingconvention.org/git/branch-naming.html)
- Sync your latest dependencies.


### Submitting a Pull Request (PR):

Before you submit your Pull Request (PR) consider the following guidelines:

1. Search GitHub for an open or closed PR that relates to your submission. You don't want to duplicate effort.

2. Be sure that an issue describes the problem you're fixing, or documents the design for the feature you'd like to add. Discussing the design up front helps to ensure that we're ready to accept your work.

3. Branch  naming  convention  must follow [Branch Naming](https://namingconvention.org/git/branch-naming.html).

4. Fork repo.

5. Make your changes in a new git branch:
   ```
   git checkout -b my-fix-branch master
   ```
6. Create your patch, including appropriate test cases.

7. Run linting and ensure that all errors are fixed.

8. Run tests and ensure that all tests pass.

9. Commit your changes using a descriptive commit message

   ```
   git commit -a
   ```

   Note: the optional commit -a command line option will automatically "add" and "rm" edited files.

10. Push your branch to GitHub:

    ```
    git push origin my-fix-branch
    ```

11. In GitHub, send a pull request to the appropriate branch (usually dev branch)

12. If we suggest changes, make the required updates.

13. Re-run the tests to ensure tests are still passing.

14. Rebase your branch and force push to your GitHub repository (this will update your Pull Request):
    ```
    git rebase master -i
    git push -f
    ```

That's it! Thank you for your contribution!

After your pull request is merged, you can safely delete your branch and pull the changes from the main (upstream) repository:

1. Delete the remote branch on GitHub either through the GitHub web UI or your local shell as follows:

   ```
   git push origin --delete my-fix-branch
   ```

2. Check out the master branch:

   ```
   git checkout master -f
   ```

3. Delete the local branch:

   ```
   git branch -D my-fix-branch
   ```

4. Update your master with the latest upstream version:
   ```
   git pull --ff upstream master
   ```

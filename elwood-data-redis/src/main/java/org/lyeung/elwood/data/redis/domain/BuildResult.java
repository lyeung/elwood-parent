package org.lyeung.elwood.data.redis.domain;

import org.lyeung.elwood.data.redis.domain.enums.BuildStatus;

import java.util.Date;

/**
 * Created by lyeung on 23/08/2015.
 */
public class BuildResult extends AbstractDomain {

    private BuildStatus buildStatus;

    private Date startRunDate;

    private Date finishRunDate;

    public BuildStatus getBuildStatus() {
        return buildStatus;
    }

    public void setBuildStatus(BuildStatus buildStatus) {
        this.buildStatus = buildStatus;
    }

    public Date getStartRunDate() {
        return startRunDate;
    }

    public void setStartRunDate(Date startRunDate) {
        this.startRunDate = startRunDate;
    }

    public Date getFinishRunDate() {
        return finishRunDate;
    }

    public void setFinishRunDate(Date finishRunDate) {
        this.finishRunDate = finishRunDate;
    }
}
